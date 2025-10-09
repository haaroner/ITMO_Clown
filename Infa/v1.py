data = list(map(int, list(input())))

r1 = (data[2]+data[4]+data[6])%2
r2 = (data[2]+data[5]+data[6])%2
r3 = (data[4]+data[5]+data[6])%2

S1 = (r1 + data[0])%2
S2 = (r2 + data[1])%2
S3 = (r3 + data[3])%2
er = int(str(S1)+str(S2)+str(S3), 2)
data[er-1] = abs(data[er-1] - 1)
data.pop(0)
data.pop(0)
data.pop(1)
print(data)
