input_t = list(input())
if(len(input_t) != 7):
    print("wrong len")
elif(input_t.count('0')+input_t.count('1') != 7):
    print("wrong input")
else:
    data = list(map(int, input_t))

    r1 = (data[2]+data[4]+data[6])%2
    r2 = (data[2]+data[5]+data[6])%2
    r3 = (data[4]+data[5]+data[6])%2

    S1 = (r1 + data[0])%2
    S2 = (r2 + data[1])%2
    S3 = (r3 + data[3])%2
    if(S1 != 0 or S1 != 0 or S2 != 0):
        print("error in message")
    else:
        print("norm message")
    er = int(str(S1)+str(S2)+str(S3), 2)
    data[er-1] = abs(data[er-1] - 1)
    data.pop(0)
    data.pop(0)
    data.pop(1)
    print(data)
