Проверять на:

http://localhost:8080
http://localhost:8080/api/v1/book/

Это - демонстрация Retry.
Выбрасываем исключение в BookService.
При сбое BookService.findAll -  показываем список из книг-заглушек.
