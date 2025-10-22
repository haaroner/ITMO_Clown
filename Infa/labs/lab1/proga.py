from itertools import product

#calculate certain fibonachi number
def fib(num):
    nums = [1, 1]
    while(len(nums) < num):
        nums.append(nums[len(nums)-1]+nums[len(nums)-2])
    return nums[len(nums)-1]

#convert to decimal
def to_10(num, arg = 0):
    s = str(num)
    l = len(s)
    r = 0
    for i in range(l):
        print(s[l-i-1], '  ', fib(i+2))
        r+= fib(i+2)*int(s[l-i-1])
    return r


k = 0

print(to_10(101010100))
