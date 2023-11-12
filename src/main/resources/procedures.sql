CREATE OR REPLACE PROCEDURE create_client_contact(number VARCHAR, type VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO client_contact (number, type)
    VALUES (create_client_contact.number, create_client_contact.type);
END
$$;



CREATE OR REPLACE PROCEDURE update_client_full_name(client_id BIGINT, new_first_name VARCHAR, new_last_name VARCHAR)
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE client
    SET first_name = new_first_name,
        last_name  = new_last_name
    WHERE client.client_id = update_client_full_name.client_id;
END;
$$;



CREATE OR REPLACE PROCEDURE delete_client(id INT)
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM client WHERE client_id = id;
END;
$$;



CREATE OR REPLACE PROCEDURE change_plan(contract_id1 INT, new_plan VARCHAR)
    LANGUAGE plpgsql AS
$$
    DECLARE id_of_plan INT;
BEGIN
    IF new_plan != 'Бюджетный' AND new_plan != 'Рабочий' AND new_plan != 'Максимальный'
    THEN
        RAISE EXCEPTION 'Invalid plan!';
    end if;

    SELECT plan_id INTO id_of_plan
    FROM tariff_plan
    WHERE plan_name = new_plan;

    UPDATE contract SET plan_id = id_of_plan WHERE contract_id = change_plan.contract_id1;
END;
$$