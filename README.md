WeatherForecast
===============

WeatherForecast project
Створити програму Weather Forecast. На головній активити попросити користувача вказати погоду сьогодні. А далі за принципом укргідрометцентру:)))
генеруєте випадкове число в діапазоні від 0 до 1, позначимо R
якщо сьогодні сонячно, то множимо R на 0.9
якщо сьогодні хмарно, то множимо R на 0.8
якщо сьогодні дощ, то множимо R на 0.7
якщо сьогодні сніг, то множимо R на 0.7

R > 0.5 -> завтра сонячно
0.35 < R <= 0.5 - хмарно
R <= 0.35 && R >= 0.1 - дощ
R < 0.1 -> сніг

Відповідне повідомлення з зображенням виводите користувачеві програми.
