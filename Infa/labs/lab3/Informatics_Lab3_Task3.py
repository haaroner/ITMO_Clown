#Author = Ezhelev Georgii Igorevich
#Group = P3132
#Date = 23.10.2025

import re
data_test = ['F{D`4S5I]?m]a)u1qc%p~9a' ,'Edik0decemberaC52x35iX4JyY', 'W@!|Gp4?9Wlp:2::Ag}w>(RQT',
        'KL4D0VK4_P4VLU*xx!July23>13377331WxR', 'KL4D0VK4_P4VLU*xx!August23>1331WxR']
print("input test data:", data_test)
print("\n"*3)

while True:
    inp = input('type pswd')
    if(inp == 'test'):
        data = data_test
    else:
        data = [inp]
    for s in data:
        print("your password:   ", s)
        
        res = [0, 0, 0, 0, 0, 0]
        r1 = re.findall(r'.{5}', s)
        res[0] = r1 !=[]
        if(res[0]==False):
            print("Your password must be at least 5 characters")
        
        r2 = re.findall(r'\d\d+', s)
        res[1] = r2!=[]
        if(res[1]==False):
            print("Your password must include a number")

        r3 = re.findall(r'[A-Z]',s)
        res[2] = r3 != []
        if(res[2]==False):
            print("Your password must include Upper case letter")
            
        r4 = re.findall(r'\W', s)
        res[3] = r4 != []
        if(res[3] == False):
            print("Your password must include special character")

        r5 = re.findall(r'[0-9]',s)
        res[4] = sum(map(int, r5)) == 25
        if(res[4]==False):
            print("sum of digits must add up to 25")

        r6 = re.findall(r'(january)|(february)|(march)|(April)|(June)|(july)|(August)|(September)|(October)|(November)|(December)',s, re.IGNORECASE)
        res[5] = r6 != []
        if(res[5]==False):
            print("Your password must include name of the month")

        if(sum(res) == 6):
            print("PASSWORD IS VALID")
        print("\n"*3)      
