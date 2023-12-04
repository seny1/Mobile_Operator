package org.elSasen.service;

import org.elSasen.dao.ExtraServiceDao;
import org.elSasen.dto.insert.ExtraServiceDtoInsert;
import org.elSasen.dto.select.ExtraServiceDto;
import org.elSasen.dto.select.ProductDto;
import org.elSasen.entities.ExtraService;
import org.elSasen.entities.Product;
import org.elSasen.entities.ServiceCategory;
import org.elSasen.exception.ValidationException;
import org.elSasen.mapper.ExtraServiceMapper;
import org.elSasen.validator.Error;
import org.elSasen.validator.ExtraServiceValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtraServiceService {
    private static final ExtraServiceService INSTANCE = new ExtraServiceService();
    private final ExtraServiceDao extraServiceDao = ExtraServiceDao.getInstance();
    private final ExtraServiceValidator extraServiceValidator = ExtraServiceValidator.getInstance();
    private final ExtraServiceMapper extraServiceMapper = ExtraServiceMapper.getInstance();

    public List<ExtraServiceDto> getExtraServiceTable(String orderBy) {
        var extraServiceTable = extraServiceDao.getExtraServiceTable(orderBy);
        return extraServiceTable.stream()
                .map(extraServiceMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<String> getColumnsOfExtraService() {
        return extraServiceDao.getMetaData();
    }
    public List<String> getGoodColumnsOfExtraService() {
        return extraServiceDao.getGoodMetaData();
    }
    public List<ExtraServiceDto> getFilterExtraServiceTable(String orderBy, Map<String, String> filterMap) {
        var extraServiceTable = extraServiceDao.getExtraServiceTable(orderBy);
        var result = new ArrayList<ExtraService>();
        result = (ArrayList<ExtraService>) extraServiceTable;
        for (int i = 0; i < extraServiceTable.size();) {
            var extraService = extraServiceTable.get(i);
            if (!filterMap.get("serviceName").isEmpty() && !extraService.getServiceName().equals(filterMap.get("serviceName"))) {
                result.remove(i);
            } else if (!filterMap.get("priceUp").isEmpty() && !(extraService.getPrice() > Integer.parseInt(filterMap.get("priceUp")))) {
                result.remove(i);
            } else if (!filterMap.get("priceDown").isEmpty() && !(extraService.getPrice() < Integer.parseInt(filterMap.get("priceDown")))) {
                result.remove(i);
            } else if (!filterMap.get("categoryName").isEmpty() && !(extraService.getCategory().getName().equals(filterMap.get("categoryName")))) {
                result.remove(i);
            } else {
                i++;
            }
        }
        return result.stream()
            .map(extraServiceMapper::mapFrom)
            .collect(Collectors.toList());
    }

    public List<String> getServices() {
        return extraServiceDao.getServices();
    }

    public void deleteService(String name){
        if(!extraServiceDao.deleteService(name)){
            throw new ValidationException(List.of(Error.of("invalid.product", "Услуги с таким названием не существует")));
        }
    }

    public void insertIntoService(ExtraServiceDtoInsert extraServiceDtoInsert) {
        var validationResult = extraServiceValidator.isValid(extraServiceDtoInsert);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var extraService = extraServiceMapper.mapFrom(extraServiceDtoInsert);
        extraServiceDao.insertIntoService(extraService);
    }

    public static ExtraServiceService getInstance() {
        return INSTANCE;
    }
}
