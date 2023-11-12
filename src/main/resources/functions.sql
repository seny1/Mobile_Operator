create function find_calls_by_client_id(id bigint)
    returns TABLE
            (
                name     text,
                sub      character varying,
                duration double precision
            )
    language plpgsql
as
$$
BEGIN
    return query
        SELECT concat(c.first_name, ' ', c.last_name), call.subscriber_number, call.call_duration
        FROM call
                 JOIN public.client c on call.client_id = c.client_id
        WHERE call.client_id = id;
END
$$;



create or replace function find_min_count_of_products_in_checks()
    returns table
            (
                product VARCHAR,
                min     BIGINT
            )
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT product_name, SUM(product_count)
        FROM "Check"
                 JOIN public.product p2 on p2.product_id = "Check".product_id
        GROUP BY product_name
        HAVING SUM(product_count) = (SELECT MIN(sum)
                                     FROM (SELECT SUM(product_count) AS sum
                                           FROM "Check"
                                                    JOIN public.product p on p.product_id = "Check".product_id
                                           GROUP BY product_name) AS t1);
END;
$$;



create function print_check("check" bigint)
    returns TABLE
            (
                name  character varying,
                price double precision
            )
    language plpgsql
as
$$
BEGIN
    return query
        SELECT p.product_name, p.price * "Check".product_count
        FROM "Check"
                 JOIN public.product p on "Check".product_id = p.product_id
        WHERE check_id = "check";
end;
$$;



create or replace function avg_order()
    returns double precision
    language plpgsql
AS
$$
DECLARE
    avg_result double precision;
BEGIN
    SELECT avg(price)
    into avg_result
    FROM "Order"
             JOIN public.extra_service es on "Order".service_id = es.service_id;
    return avg_result;
END;
$$;



create or replace function check_free_masters()
    returns table
            (
                master text
            )
    language plpgsql
AS
$$
BEGIN
    CREATE TEMPORARY TABLE count_of_all_orders
    (
        employee_id BIGINT,
        count       BIGINT
    );
    INSERT INTO count_of_all_orders (employee_id, count)
    SELECT employee_id, count(*)
    FROM "Order"
    GROUP BY employee_id;

    CREATE TEMPORARY TABLE count_of_finished_orders
    (
        employee_id    BIGINT,
        finished_count BIGINT
    );
    INSERT INTO count_of_finished_orders
    SELECT employee_id, count(*)
    FROM "Order"
             JOIN public.status s on s.status_id = "Order".status_id
    WHERE s.status_id = 4
    GROUP BY employee_id;

    CREATE TEMPORARY TABLE in_not_work
    (
        employee_id BIGINT
    );
    INSERT INTO in_not_work
    SELECT employee_id
    FROM employee
    WHERE post_id = 8
    EXCEPT
    SELECT
    employee_id
    FROM "Order";

    RETURN QUERY
        SELECT concat(first_name, ' ', last_name)
        FROM count_of_finished_orders finish
                 JOIN count_of_all_orders alls ON alls.employee_id = finish.employee_id
                 JOIN employee em ON em.employee_id = finish.employee_id
        WHERE count - finished_count = 0
        UNION
        SELECT concat(first_name, ' ', last_name)
        FROM in_not_work
                 JOIN public.employee e on in_not_work.employee_id = e.employee_id;
    DROP TABLE in_not_work;
    DROP TABLE count_of_all_orders;
    DROP TABLE count_of_finished_orders;
END;
$$