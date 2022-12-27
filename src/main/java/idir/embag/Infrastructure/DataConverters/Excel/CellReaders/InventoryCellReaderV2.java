package idir.embag.Infrastructure.DataConverters.Excel.CellReaders;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;

public class InventoryCellReaderV2 implements IExcelCellReader {

    Workbook workbook;
    EInventoryAttributes[] attrbs;

    private static final int ARTICLE_ID = 0;
    private static final int ARTICLE_NAME = 1;
    private static final int ARTICLE_CODE = 2;
    private static final int FAMILY_CODE = 3;

    private static final int ARTICLE_ID_COL = 1;
    private static final int ARTICLE_NAME_COL = 2;
    // private static final int ARTICLE_CODE_COL = 1;
    // private static final int FAMILY_CODE_COL = -1;

    public InventoryCellReaderV2() {

        attrbs = new EInventoryAttributes[] {
                EInventoryAttributes.ArticleId,
                EInventoryAttributes.ArticleName,
                EInventoryAttributes.ArticleCode,
                EInventoryAttributes.FamilyCode,
        };

    }

    @Override
    public Collection<AttributeWrapper[]> readData(ImportWrapper importWrapper) {
        Sheet sheet = workbook.getSheetAt(0);

        Collection<AttributeWrapper[]> data = new ArrayList<>();

        int rowIndex = importWrapper.getStartRow();

        AttributeWrapper[] parsedAttributes;
        
        while (rowIndex <= importWrapper.getEndRow()) {
        Row row = sheet.getRow(rowIndex);

        parsedAttributes = readCells(row);

        if (parsedAttributes == null) {
            System.out.println("Row " + rowIndex + " is null");
        rowIndex++;
        continue;
        }

        data.add(parsedAttributes);

        rowIndex++;

        }


        return data;

    }

    @Override
    public void setup(Workbook workbook) {
        this.workbook = workbook;

    }

    private AttributeWrapper[] readCells(Row row) {
        AttributeWrapper[] attributes = new AttributeWrapper[attrbs.length];

        try {

            int articleId = GsonSerialiser.deserialise(row.getCell(ARTICLE_ID_COL).getStringCellValue(), Integer.class);

            String articleName = row.getCell(ARTICLE_NAME_COL).getStringCellValue();

            if (articleName == null ) {
                return null;
            }

            if (articleName.equals("")) {
                return null;
            }


            String sArticleCode = String.valueOf(articleId).substring(0, 4);
            int familyCode = Integer.parseInt(sArticleCode);

            attributes[ARTICLE_ID] = new AttributeWrapper(attrbs[ARTICLE_ID], articleId);
            attributes[ARTICLE_CODE] = new AttributeWrapper(attrbs[ARTICLE_CODE], articleId);
            attributes[ARTICLE_NAME] = new AttributeWrapper(attrbs[ARTICLE_NAME], articleName);

            attributes[FAMILY_CODE] = new AttributeWrapper(attrbs[FAMILY_CODE], familyCode);
        } catch (Exception e) {
            return null;
        }
        return attributes;
    }

}
