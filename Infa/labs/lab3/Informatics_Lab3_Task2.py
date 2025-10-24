#Author = Ezhelev Georgii Igorevich
#Group = P3132
#Date = 23.10.2025

import re
data_test = ['502355@niuitmo.ru', 'comma.robocup@gmail.com', '-ya@mail.ru', '@mail.ru', 'ya@mailru']
print("input test data:", data_test)
print("\n"*3)

while True:
    inp = input('type mail')
    if(inp == 'test'):
        data = data_test
    else:
        data = [inp]
    for s in data:
        result = re.fullmatch(r'([0-9a-z_.]+)([@])([0-9a-z]+)([.])[a-z]+', s, re.IGNORECASE);
        if(result != None):
            result = result.group()
            print(s,"   ", result[result.find('@')+1::])
        else:
            print(s, "   Fail!")        
