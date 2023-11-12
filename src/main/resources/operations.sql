--Проекция--
SELECT first_name, last_name
FROM employee;

--Селекция--
SELECT *
FROM employee
WHERE department_id = 1;

--Соединение--
SELECT *
FROM employee,
     department
WHERE employee.department_id = department.department_id;

--Объединение--
SELECT first_name, last_name
FROM employee
UNION
SELECT first_name, last_name
FROM client;

--Пересечение--
SELECT first_name, last_name
FROM employee
WHERE salon_id = 1
INTERSECT
SELECT first_name, last_name
FROM employee
WHERE department_id = 1;

--Разность--
SELECT first_name, last_name
FROM employee
WHERE salon_id = 1
EXCEPT
SELECT first_name, last_name
FROM employee
WHERE department_id = 1;

--Группировка--
SELECT department_name, count(*)
FROM employee
         JOIN public.department d ON d.department_id = employee.department_id
GROUP BY department_name;

--Сортировка--
SELECT last_name, first_name
FROM employee
order by last_name;

--Деление--
SELECT concat(first_name, ' ', last_name) AS full_name
FROM contract
         JOIN public.client c on c.client_id = contract.client_id
         JOIN public.tariff_plan tp on tp.plan_id = contract.plan_id
WHERE plan_name IN ('Бюджетный', 'Максимальный')
GROUP BY concat(first_name, ' ', last_name)
HAVING count(*) = 2;