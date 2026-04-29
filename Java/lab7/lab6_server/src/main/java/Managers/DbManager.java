package managers;

import builders.CoordinatesBuilder;
import builders.LocationBuilder;
import builders.RouteBuilder;
import models.Coordinates;
import models.Location;
import models.Route;
import utility.Element;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class DbManager {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s502355"; // Ваш логин
    private static final String PASS = "IMWN#6870";
    private DbManager () {

    }

    private static class DbHolder {
        private static final DbManager INSTANCE = new DbManager();
    }

    public static DbManager getInstance() {
        return  DbHolder.INSTANCE;
    }

    public synchronized boolean checkIsUserRegistered(String user) {
        String sql = "SELECT 1 FROM USERS WHERE USER_NAME = ? LIMIT 1";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user);
            try(ResultSet routesResult = statement.executeQuery()) {
                return routesResult.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean checkIsUserAuth(String user, String pswd) {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? LIMIT 1";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(sql)){
            //System.out.println("auth data: " + user + pswd);
            statement.setString(1, user);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                byte[] hashDB = result.getBytes("HASH");
                String salt = result.getString("SALT");
                String pepper = SystemManager.getInstance().getPepper();
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashCalc = md.digest((pswd + salt + pepper).getBytes("UTF-8"));
                //System.out.println("Auth =" + Arrays.equals(hashCalc, hashDB));
                //System.out.println(Arrays.toString(hashCalc) + "  " + Arrays.toString(hashDB));
                //System.out.println(salt);
                return Arrays.equals(hashCalc, hashDB);
            }
            else {
                System.out.println("no such user");
                return false;
            }
        } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return false;
        }
    }

    public synchronized void registerUser(String user, String pswd) {
        String sql = "INSERT INTO USERS (USER_NAME, SALT, HASH) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String salt = java.util.UUID.randomUUID().toString();
            byte[] hashBytes = md.digest((pswd + salt + SystemManager.getInstance().getPepper()).getBytes("UTF-8"));
            statement.setString(1, user);
            statement.setString(2,salt);
            statement.setBytes(3, hashBytes);
            statement.executeUpdate();
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public <T extends Element> void addItem(T newData, Connection connection) throws SQLException {
        if(Objects.nonNull(newData)) {
            //System.out.println("start");

            PreparedStatement statement = connection.prepareStatement(newData.getSaveQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
            newData.formSaveQuery(statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Integer newId = Math.toIntExact(generatedKeys.getLong(1));
                    //System.out.println(newId);
                    newData.setId(newId);
                    //System.out.println("new ID:" + newId);
                }
            }
            statement.close();
            //System.out.println("end");

        }
    }

    public boolean addWholeRoute(Route newRoute, Connection connection, String user) throws SQLException {
            try {
                connection.setAutoCommit(false);
                addItem(newRoute.getFrom(), connection);
                addItem(newRoute.getTo(), connection);
                addItem(newRoute.getCoordinates(), connection);
                newRoute.updateLinks();
                //System.out.println("123");
                newRoute.setOwner(user);
                addItem(newRoute, connection);
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }
            catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
    }

    public boolean addWholeRoute(Route newRoute, String user, String pswd) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);) {
            addWholeRoute(newRoute, connection, user);
            return true;
        } catch (SQLException e) {
            System.err.println("Cannot initiate connection with database, error message :\n" + e.getMessage());
            return false;
        }
    }

    public boolean cheсkRouteOwner(Connection connection, Integer id, String user, String pswd) {
        String sql = "SELECT USERS.USER_NAME as NAME, USERS.SALT as SALT, USERS.HASH as HASH FROM ROUTES LEFT JOIN USERS ON USERS.USER_NAME = ROUTES.OWNER WHERE ROUTES.ROUTE_ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    String salt = resultSet.getString("SALT");
                    byte[] hashDB = resultSet.getBytes("HASH");
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hashBytes = md.digest((pswd + salt + SystemManager.getInstance().getPepper()).getBytes("UTF-8"));
                   // System.out.println("Owner: " + (Objects.equals(name, user) && Arrays.equals(hashDB, hashBytes)));
                  //  System.out.println(Arrays.toString(hashDB) + " " + Arrays.toString(hashBytes));
                    return Objects.equals(name, user) && Arrays.equals(hashDB, hashBytes);
                }
            }

        } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeWholeRoute(Integer id, Connection connection, String user, String pswd) throws SQLException {
           if(cheсkRouteOwner(connection, id, user, pswd)) {
               try (PreparedStatement statement = connection.prepareStatement(Route.getRemoveQuery());) {
                   statement.setInt(1, id);
                   int affectedRows = statement.executeUpdate();

                   if (affectedRows > 0) System.out.println("success");
                   else System.out.println("No such route");
                   return true;
               }
           }
           else{
               System.out.println("Route is belonging to another user");
               return false;
           }
    }

    public boolean removeWholeRoute(Integer id, String user, String pswd) {
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);) {
            return removeWholeRoute(id, connection, user, pswd);
        }
        catch (SQLException e) {
            System.err.println("Cannot initiate connection with database, error message :\n" + e.getMessage());
            return false;
        }
    }

    public boolean updateWholeRoute(Integer id, Route newRoute, String user, String pswd){
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS);) {
            if(cheсkRouteOwner(connection, id, user, pswd)) {
                if (removeWholeRoute(id, connection, user, pswd))
                    addWholeRoute(newRoute, connection, user);
            }
            else
                return false;
        } catch (SQLException e) {
            System.err.println("Cannot initiate connection with database, error message :\n" + e.getMessage());
            return false;
        }
        return true;
    }

    public <T extends Element> List<List<?>> loadCollectionFromDb() {
        String coordsSql = "SELECT * FROM COORDINATES";
        String locsSql = "SELECT * FROM LOCATIONS";
        String routesSql = "SELECT * FROM ROUTES";
        List<List<?>> result = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            ResultSet coordsResult = statement.executeQuery(coordsSql);
            List<Coordinates> newCoordinates = new CoordinatesBuilder().buildByResultSet(coordsResult);

            ResultSet locationsResult = statement.executeQuery(locsSql);
            List<Location> newLocations = new LocationBuilder().buildByResultSet(locationsResult);

            ResultSet routesResult = statement.executeQuery(routesSql);
            List<Route> newRoutes = new RouteBuilder().buildByResultSet(routesResult);
            statement.close();

            result.add(newRoutes);
            result.add(newCoordinates);
            result.add(newLocations);
        } catch (SQLException e) {
            System.err.println("Unable to load collection from DB, shutting down");
            System.exit(0);
        }
        return result;
    }
}
