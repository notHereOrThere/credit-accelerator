Запросы проходят через gateway MC
порядок вызов операций:
1. put: /application/offer
{
    "applicationId": 2,
    "requestedAmount": 300000,
    "totalAmount": 314500,
    "term": 15,
    "monthlyPayment": 22246.59,
    "rate": 9,
    "isInsuranceEnabled": true,
    "isSalaryClient": true
}
2. post: /application
{
  "amount": 300000,
  "term": 15,
  "firstName": "Ivan",
  "lastName": "Ivanov",
  "middleName": "Ivanovich",
  "email": "ivan@gmail.com",
  "birthdate": "2000-01-01",
  "passportSeries": "1234",
  "passportNumber": "123456"
}
3. put: /deal/calculate/{applicationId}
4. post: /deal/document/{applicationId}/send
5. post: /deal/document/{applicationId}/code
