import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract;
import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;
import static org.junit.Assert.assertTrue;

public class ExtractText {
    public static String getText(String inputImage)
    {
        BytePointer outText;
        tesseract.TessBaseAPI api = new tesseract.TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(".", "ENG") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }
        // Open input image with leptonica library
        lept.PIX image = pixRead(inputImage);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();
        assertTrue(!string.isEmpty());
        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
        return string;
    }
}
