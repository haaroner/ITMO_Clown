#Author = Ezhelev Georgii Igorevich
#Group = P3132
#Date = 23.10.2025

import re
data = ['502355@niuitmo.ru', 'comma.robocup@gmail.com', '-ya@mail.ru', '@mail.ru', 'ya@mailru']
print("input test data:", data)
print("\n"*3)
for s in data:
    result = re.fullmatch(r'([0-9a-z_.]+)([@])([0-9a-z]+)([.])[a-z]+', s, re.IGNORECASE);
    if(result != None):
        result = result.group()
        print(s,"   ", result[result.find('@')+1::])
    else:
        print(s, "   Fail!")
        
