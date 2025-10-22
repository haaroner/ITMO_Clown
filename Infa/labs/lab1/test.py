n = 46778
r = ''
while n > 0:
    r += str(n%11)
    print(str(n%11))
    n //= 11
print(r[::-1])
