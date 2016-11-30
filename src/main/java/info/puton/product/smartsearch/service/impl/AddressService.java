package info.puton.product.smartsearch.service.impl;

import info.puton.product.common.util.ExcelUtil;
import info.puton.product.common.util.PinYinUtil;
import info.puton.product.smartsearch.constant.Group;
import info.puton.product.smartsearch.constant.Origin;
import info.puton.product.smartsearch.model.Address;
import info.puton.product.smartsearch.service.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by taoyang on 2016/9/21.
 */
@Service
public class AddressService implements AddressHandler {

    @Autowired
    AddressIndexer addressIndexer;

    @Override
    public void handleFile(String filePath, Map additional) throws Exception {

        InputStream in;
        in = new FileInputStream(filePath);
        Workbook wb = WorkbookFactory.create(in);
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            throw new Exception("没有这张表");
        }

        // 遍历行
        int beginRow = 1;
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = beginRow; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            String chineseName = ExcelUtil.getTrimValue(row.getCell(0));
            String position = ExcelUtil.getStringValue(row.getCell(1));
            String fixedPhone = ExcelUtil.getStringValue(row.getCell(2));
            String mobilePhone = ExcelUtil.getStringValue(row.getCell(3));
            String organization = ExcelUtil.getStringValue(row.getCell(4));
            String department = ExcelUtil.getStringValue(row.getCell(5));
            String areaCode = ExcelUtil.getStringValue(row.getCell(6));
            String fax = ExcelUtil.getStringValue(row.getCell(7));
            String addrezz = ExcelUtil.getStringValue(row.getCell(8));
            fixedPhone = areaCode + "-" + fixedPhone;
            fax = areaCode + "-" + fax;
            String englishName = PinYinUtil.converterToSpell(chineseName);
            String firstSpell = PinYinUtil.converterToFirstSpell(chineseName);
            String id = firstSpell + mobilePhone;

            Address address = new Address();

            address.setId(id);
            address.setGroup(Group.PUBLIC);
            address.setOrigin(Origin.ADDRESS);

            address.setAccountId(id);
            address.setChineseName(chineseName);
            address.setEnglishName(englishName);
            address.setPosition(position);
            address.setFixedPhone(fixedPhone);
            address.setMobilePhone(mobilePhone);
            address.setFax(fax);
            address.setOrganization(organization);
            address.setDepartment(department);
            address.setAddress(addrezz);
            addressIndexer.addAddress(address);

        }

    }
}
