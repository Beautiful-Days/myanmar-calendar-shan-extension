package mmcalendar.shan;

import mmcalendar.MyanmarDate;

import static mmcalendar.shan.ShanDate.*;
import static mmcalendar.shan.ShanDate.ME_PEE;

public final class ShanDateKernel {

    public static int calculateShanMonth(MyanmarDate myanmarDate) {
        int shanMonth = myanmarDate.getMonth() + 4;
        if (shanMonth > 12) shanMonth = shanMonth - 12;
        return shanMonth;
    }

    public static int calculateMePee(long epochDay) {
        return (int) ((epochDay + 7) % 10);
    }

    public static int calculateLukPee(long epochDay) {
        return (int) (epochDay + 5) % 12;
    }

    public static String getLukPee(long epochDay) {
        return ShanDate.LUK_PEE[calculateLukPee(epochDay)];
    }

    public static String getMePee(long epochDay) {
        return ShanDate.ME_PEE[calculateMePee(epochDay)];
    }

    public static String getWannTai60(long epochDay) {
        return getMePee(epochDay) + getLukPee(epochDay);
    }


    // လၵ်းၼီႈပီႊမိူင်း ( ဢမ်ႇၸႂ်ပီႊၶေႇ )
    // 2109 - ၵတ်းသႂ်ႉ(ငူး)
    public static String getPeeMurng(int shanYear) {
        int year = shanYear - 3;
        int mePeeInt = year % 10; // မိင်ႈမႄႈပီႊ - ဢဝ်တူဝ်လိုၼ်း
        String mingMePee = getPreviousMePee(mePeeInt);
        int lukPeeInt = year % 12;
        String mingLukPee = getPreviousLukPee(lukPeeInt);
        return mingMePee + mingLukPee + "(မိင်ႈ" + LUK_PEE_DEF[--lukPeeInt] + ")";
    }

    // ပီႊထမ်း
    // 1996 - ၼူ
    // 1992 - လိင်း
    // 2001 - ငူး
    public static String getPeeHtam(int engYear) {
        int lukPeeRemainder = (engYear % 12) - 4;
        if (lukPeeRemainder < 0) {
            lukPeeRemainder = 12 + lukPeeRemainder;
        }
        int mePeeRemain = (engYear % 10) - 4;
        if (mePeeRemain < 0) {
            mePeeRemain = 10 + mePeeRemain;
        }
        return ME_PEE[mePeeRemain] + LUK_PEE[lukPeeRemainder] + "(မိင်ႈ" + LUK_PEE_DEF[lukPeeRemainder] + ")";
    }

    private static String getPreviousLukPee(int lukPeeInt) {
        return LUK_PEE[--lukPeeInt];
    }

    private static String getPreviousMePee(int mePeeInt) {
        return ME_PEE[--mePeeInt];
    }
}
