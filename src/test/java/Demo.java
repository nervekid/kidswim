
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cxh on 2017/6/16.
 */
public class Demo {
    public static void main(String[] args) {
        String extId = "99e8766b40af4d72a08f469f2541d186";
        String aa = "0,1,603d19daede4455880e4e1f9869fab4b,";
        System.out.println(aa.indexOf(","+extId+",")==-1);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHH24mmss");
        System.out.println(sdf.format(new Date()));

      /*  Constants constants = new Constants();
        MSProduct.sendObjectMessage("constants",constants);*/
    }
}
