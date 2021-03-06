/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/38027783
 */
package sandbox.images;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import sandbox.WrapToTest;

@WrapToTest
public class WatermarkedImages5 {
    public static final String IMAGE1 = "resources/images/bruno.jpg";
    public static final String IMAGE2 = "resources/images/dog.bmp";
    public static final String IMAGE3 = "resources/images/fox.bmp";
    public static final String IMAGE4 = "resources/images/bruno_ingeborg.jpg";
    public static final String DEST = "results/images/watermark_image5.pdf";
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new WatermarkedImages5().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte cb = writer.getDirectContentUnder();
        document.add(getWatermarkedImage(cb, Image.getInstance(IMAGE1)));
        document.add(getWatermarkedImage(cb, Image.getInstance(IMAGE2)));
        document.add(getWatermarkedImage(cb, Image.getInstance(IMAGE3)));
        Image img = Image.getInstance(IMAGE4);
        img.scaleToFit(400, 700);
        document.add(getWatermarkedImage(cb, img));
        document.close();
    }
    
    public Image getWatermarkedImage(PdfContentByte cb, Image img) throws DocumentException {
        float width = img.getScaledWidth();
        float height = img.getScaledHeight();
        PdfTemplate template = cb.createTemplate(width, height);
        template.addImage(img, width, 0, 0, height, 0, 0);
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(width);
        table.getDefaultCell().setBorderColor(BaseColor.YELLOW);
        table.addCell("Test1");
        table.addCell("Test2");
        table.addCell("Test3");
        table.addCell("Test4");
        table.writeSelectedRows(0, -1, 0, height, template);
        return Image.getInstance(template);
    }
}
