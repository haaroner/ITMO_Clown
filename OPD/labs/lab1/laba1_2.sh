#!/bin/bash
clear
chmod -R 700 lab0
rm -R lab0
mkdir -p lab0
cd lab0
#1.1
mkdir dragonite5 pikachu2 seedot5
touch ludicolo0 seaking3 venonat5

mkdir -p dragonite5/dusclops/{rampardos,arbok,vanilluxe}

#watchog created via echo  in the section below
touch dragonite5/vigoroth
#masquerain created via cat
touch dragonite5/dusclops/{scrafty,bellsprout,bidoof}

mkdir -p dragonite5/grovyle/{steelix,medicham,togetic}

touch dragonite5/grovyle/{blissey,machamp}
touch dragonite5/grovyle/{conkeldurr,lotad}


mkdir -p pikachu2/chimchar/{seel,snorunt,breloom,mismagius}
touch pikachu2/chimchar/{kabuto,nidoqueen,dugtrio}

touch pikachu2/misdreavus

mkdir -p pikachu2/deerling/{wigglytuff,mantine,metapod}
touch pikachu2/deerling/{quagsire,finneon,cacnea,karrablast}
touch pikachu2/nosepass

touch seedot5/{pidove,ralts,pidgey,spheal}


#1.2

#new file + one line echo creation
echo "Тип диеты  Herbivore" > dragonite5/watchog

#multiple lines echo creation
echo "satk=5 sdef=12" > dragonite5/dusclops/scrafty
echo "spd=6" >> dragonite5/dusclops/scrafty

#new file
cat > dragonite5/dusclops/masquerain << EOF
Развитые способности  Unnerve
EOF

cat > dragonite5/dusclops/bellsprout << EOF
Ходы  Bind 
Double-Edge Gastro Acid Giga Drain Knock Off Secret Power Seed Bomb
Sleep Talk Snore Sucker Punch Synthesis Worry Seed
EOF

cat > dragonite5/dusclops/bidoof << EOF
satk=4 
sdef=4 spd=3
EOF

cat > dragonite5/vigoroth << EOF
Возможности  Overland=9 Surface=6 Jump=4
Power=3 Intelligence=3
EOF

cat > dragonite5/grovyle/blissey << EOF
Развитые способности  Friend Guard
Healer
EOF

cat > dragonite5/grovyle/machamp << EOF
weight=286.6 height=63.0 atk=13 def=8
EOF

cat > dragonite5/grovyle/conkeldurr << EOF
Ходы
Block Drain Punch Fire Punch Helping Hand Ice Punch Knock Off Low Kick 
Sleep Talk Snore Superpower Thunderpunch
EOF

cat > dragonite5/grovyle/lotad << EOF
satk=4 sdef=5
spd=3
EOF

cat > ludicolo0 << EOF
Тип покемона  WATER GRASS
EOF

cat > pikachu2/misdreavus << EOF
Возможности
Overland=1 Surface=1 Sky=8 Power=1 Intelligence=4 Invisibility=0
Phasing=0
EOF

cat > pikachu2/chimchar/kabuto << EOF
Ходы  Ancientpower Body Slam Brine Double-Edge Earth
Power Giga Drain Icy Wind Iron Defense Knock Off Rollout Sleep Talk
Snore Stealth Rock Water Pulse
EOF

cat > pikachu2/chimchar/nidoqueen << EOF
satk=8 sdef=9
spd=8
EOF

cat > pikachu2/chimchar/dugtrio << 'EOF'
Paзвитые способности Sand Force
EOF

cat > pikachu2/deerling/quagsire << 'EOF'
Paзвитые
способности Unaware
EOF

cat > pikachu2/deerling/finneon << 'EOF'
satk=5 sdef=6 spd=7
EOF

cat > pikachu2/deerling/cacnea << 'EOF'
Тип диеты
Phototroph
EOF

cat > pikachu2/deerling/karrablast << 'EOF'
Ходы  Bug Bite Giga Drain Iron Defense Knock
Off Sleep Talk Snore
EOF

cat > pikachu2/nosepass << 'EOF'
Живет  Beach Cave
Mountain
EOF

cat > seaking3 << 'EOF'
Тип диеты  Herbivore
EOF

cat > seedot5/pidove << 'EOF'
Paзвитые способности
Rivalry
EOF

cat > seedot5/ralts << EOF
Тип покемона PSYCHIC NONE
EOF

cat > seedot5/pidgey << EOF
Тип покемона  NORMAL
FLYING
EOF

cat > seedot5/spheal << EOF
Способности Freezing Point Torrent Thick Fat Ice
Body
EOF

cat > venonat5 << EOF
Ходы  Bug Bite Disable Double-Edge Giga Drain Signal
Beam Skill Swap Sleep Talk Snore String Shot Swift Zen Headbutt
EOF
#2
chmod u=rwx,g=rx,o=w dragonite5            
chmod u=rw,g=,o=r dragonite5/watchog    

chmod 771 dragonite5/dusclops   
chmod 543 dragonite5/dusclops/rampardos  
chmod 752 dragonite5/dusclops/arbok

chmod 006 dragonite5/dusclops/scrafty  
chmod u-rw dragonite5/dusclops/masquerain 
chmod 513 dragonite5/dusclops/vanilluxe        
chmod 046 dragonite5/dusclops/bellsprout        
chmod 404 dragonite5/dusclops/bidoof              

chmod u+w,g-r,o-r dragonite5/vigoroth           
chmod 736 dragonite5/grovyle                       
chmod 736 dragonite5/grovyle/steelix                
chmod 753 dragonite5/grovyle/medicham              
chmod 664 dragonite5/grovyle/blissey               
chmod 066 dragonite5/grovyle/machamp               
chmod 711 dragonite5/grovyle/togetic              

chmod u=rw,g=w,o= dragonite5/grovyle/conkeldurr           
chmod u=,g=,o=rw dragonite5/grovyle/lotad                  

chmod 620 ludicolo0                      
chmod u=rx,g=rwx,o=rw pikachu2                      
chmod u=,g=rw,o= pikachu2/misdreavus           
chmod 571 pikachu2/chimchar             
chmod 576 pikachu2/chimchar/seel         
chmod 044 pikachu2/chimchar/kabuto       
chmod 004 pikachu2/chimchar/nidoqueen    
chmod 771 pikachu2/chimchar/snorunt     
chmod 513 pikachu2/chimchar/breloom     
chmod u=wx,g=rw,o=wx pikachu2/chimchar/mismagius   

chmod 600 pikachu2/chimchar/dugtrio

chmod 375 pikachu2/deerling
chmod 644 pikachu2/deerling/quagsire
chmod 004 pikachu2/deerling/finneon
chmod 753 pikachu2/deerling/wigglytuff
chmod 550 pikachu2/deerling/mantine
chmod 600 pikachu2/deerling/cacnea
chmod 335 pikachu2/deerling/metapod
chmod 644 pikachu2/deerling/karrablast

chmod 620 pikachu2/nosepass

chmod 046 seaking3

chmod 771 seedot5
chmod 640 seedot5/pidove
chmod 066 seedot5/ralts
chmod 624 seedot5/pidgey
chmod a=r seedot5/spheal
chmod 046 venonat5

#3
echo section 3

ln venonat5 dragonite5/dusclops/scraftyvenonat
chmod u=rwx pikachu2/chimchar/seel 
chmod u=rwx seedot5/ralts 
cp -r seedot5 pikachu2/chimchar/seel

chmod 066 seedot5/ralts
chmod 576 pikachu2/chimchar/seel  

echo ""

cp ludicolo0 pikachu2/deerling/wigglytuff/
ln -s venonat5 dragonite5/watchogvenonat
ln -s venonat5 seedot5/sphealvenonat

chmod 400 venonat5
cat venonat5 > dragonite5/vigorothvenonat
cat venonat5 > dragonite5/dusclops/masquerainvenonat
# no chmod 046 venonat5  file used later


cp ludicolo0 pikachu2/deerling/wigglytuff
cat pikachu2/chimchar/dugtrio dragonite5/vigoroth seedot5/pidgey > ludicolo0_25

chmod u+r dragonite5/dusclops/masquerain pikachu2/chimchar/kabuto pikachu2/deerling/finneon
cat dragonite5/dusclops/masquerain pikachu2/chimchar/kabuto pikachu2/deerling/finneon > seaking3_29
chmod u-r dragonite5/dusclops/masquerain pikachu2/chimchar/kabuto pikachu2/deerling/finneon

ln -s dragonite5 Copy_41
ln seaking3 dragonite5/dusclops/masquerainseaking
ln -s seedot5 Copy_76
#List files tree
#ls -lR

#4
echo section 4
cat *5 */*5 */*/*5 2>/dev/null | wc -l 

ls -lRt 2>/dev/null | grep "studs"| tail -n 2 

echo ""

cat dragonite5/* | sort -r

echo""

cat ludicolo0 2>&1 | grep -vn "y$"

echo ""

cat pikachu2/* 2>/dev/null| sort -r | cat -n

echo ""

ls -lRt 2>&1 | grep "studs" |head -n 4

echo ""

cat seedot5/{pidove,ralts,pidgey,spheal} | grep -n "fen"

echo ""

ls -lRS v* */v* */*/v* 2>/dev/null | grep "studs"| head -n 2

echo ""

cat dragonite5/dusclops/{masquerain,bellsprout,bidoof} dragonite5/vigoroth dragonite5/grovyle/{blissey,machamp,conkeldurr,lotad} pikachu2/misdreavus pikachu2/chimchar/kabuto 2>&1 | grep -v "ron" 2>&1

echo ""

cat *5 */*5 */*/*5 | sort -r 

echo ""
mkdir tmp
touch tmp/output
chmod 777 venonat5
cat venonat5 2>>tmp/output | wc -m >> venonat5 2>>tmp/output
chmod 046 venonat5

echo ""

cat seedot5/* 2>>tmp/output | grep -v "y$"

#5
echo "section 5"
rm -f seaking3 2>/dev/null
rm -f dragonite5/dusclops/bidoof
rm -f dragonite5/watchogvenon*
rm -f dragonite5/scraftyvenon*
rm -Rf dragonite5
cd ..
chmod -R u+rw lab0
rm -Rf lab0/pikachu2/chimchar
#ls -lR
