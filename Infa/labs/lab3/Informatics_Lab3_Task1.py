#Author = Ezhelev Georgii Igorevich
#Group = P3132
#Date = 23.10.2025

import re
data_test = ["Вечер за окном. / Ещё день прожит. / Жизнь скоротечна...",
        'Вечерним вьюнком / В плен захвачен… Недвижно / Стою в забытьи.',
        'В небе такая луна, / Словно дерево спилено под корень: / Белеет свежий срез.',
        'Я в плен захвачен… Недвижно / Вечерним вьюнком/ Стою в забытьи.',
        'проверка количества слогов / должна выдать ошибку']

print("input test data:", data_test)
print("\n"*3)
while True:
    inp = input('type haiku')
    if(inp == 'test'):
        data = data_test
    else:
        data = [inp]
    for s in data:
        print("cur msg:   ", s)
        result = re.split(r'[/]', s)

        if(len(result)!=3):
            print("Не хайку! Должно быть 3 слога")
        else:
            r1 = len(re.findall(r'[aeiouаеёиоуыэюя]', result[0], re.IGNORECASE)) == 5
            r2 = len(re.findall(r'[aeiouаеёиоуыэюя]', result[1], re.IGNORECASE)) == 7
            r3 = len(re.findall(r'[aeiouаеёиоуыэюя]', result[2], re.IGNORECASE)) == 5
            if(r1+r2+r3==3):
                print("Хайку!")
            else:
                print("Не хайку!")
        print('\n')
    
