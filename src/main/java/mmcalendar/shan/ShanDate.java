package mmcalendar.shan;

import mmcalendar.Astro;
import mmcalendar.MyanmarDate;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

import static mmcalendar.shan.ShanDateKernel.*;

public class ShanDate {

    public static final String[] ME_PEE = {"ၵၢပ်ႇ", "လပ်း", "ႁၢႆး", "မိူင်း", "ပိုၵ်း", "ၵတ်း", "ၶုတ်း", "ႁုင်ႉ", "တဝ်ႇ", "ၵႃႇ"};

    public static final String[] LUK_PEE = {"ၸႂ်ႉ", "ပဝ်ႉ", "ယီး", "မဝ်ႉ", "သီ", "သႂ်ႉ", "သီင", "မူတ်ႉ", "သၼ်", "ႁဝ်ႉ", "မဵတ်ႉ", "ၵႂ်ႉ"};

    public static final String[] LUK_PEE_DEF = {"ၼူ", "ၵႂၢႆး", "သိူဝ်", "ပၢင်တၢႆး", "ငိူၵ်ႈ", "ငူး", "မႃႉ", "ပႄႉ", "လိင်း", "ၵႆႇ", "မႃ", "မူ"};

    private static final String[] WANN_MWE = {
            "ႁိူၼ်း", "ၼႃး", "မေႃႈႁႆ", "လိၼ်", "ၼမ်ႉ", "ၵဵင်း", "ၶုၼ်", "ထဝ်ႈၵႄႇ", "မႆႉ", "လုၵ်ႈဢွၼ်ႇ",
            "မၢဝ်ႇသၢဝ်", "ရႁၢၼ်း", "ၵႃႈ", "သင်ႇၶႃႇ", "ၽီ", "ၶႅၵ်ႇ", "ထဝ်ႈၵႄႇ", "လိၼ်", "မေႃႈႁႆ", "မႆႉ",
            "မၢဝ်ႇသၢဝ်", "တဝ်ႊၾႆး", "ႁိူၼ်း", "ရႁၢၼ်း", "ၼမ်ႉ", "ၶဝ်ႈ", "တူဝ်သီႇတိၼ်", "တၢင်း", "ၶုၼ်", "ၶႃႈၵူၼ်း"
    };

    private static final String[] WANN_PHEE_KIN = {
            "ၽီ", "ၵူၼ်း", "ၵႆႇ", "မႃ", "မႃႉ", "မူ", "ဝူဝ်း", "ၵႂၢႆး", "ၽီ", "ၵူၼ်း",
            "ၵႆႇ", "ပဵတ်း", "မူ", "မႃ", "ဝူဝ်း", "ၽီ", "ၵူၼ်း", "ၵႆႇ", "မႃ", "မႃႉ",
            "မူ", "ဝူဝ်း", "ၵႂၢႆး", "ၽီ", "ၵူၼ်း", "ၵႆႇ", "ပဵတ်း", "မူ", "မႃ", "ဝူဝ်း"
    };

    private final MyanmarDate myanmarDate;

    private final int shanMonth;

    private final int mePee;

    private final int lukPee;

    private ShanDate(MyanmarDate myanmarDate) {
        this.myanmarDate = myanmarDate;
        this.shanMonth = calculateShanMonth(myanmarDate);
        long epochDay = myanmarDate.toMyanmarLocalDate().toEpochDay();
        this.mePee = calculateMePee(epochDay);
        this.lukPee = calculateLukPee(epochDay);
    }

    public static ShanDate of(MyanmarDate myanmarDate) {
        return new ShanDate(myanmarDate);
    }

    public static ShanDate of(LocalDate myanmarLocalDate) {
        return of(MyanmarDate.of(myanmarLocalDate));
    }

    //-----------------------------------------------------------------------

    public static int calculateShanYear(MyanmarDate myanmarDate) {
        if (myanmarDate.getMonth() > 8 || myanmarDate.getMonth() == 1) {
            return (myanmarDate.getYearValue() + 733);
        }
        return (myanmarDate.getYearValue() + 732);
    }

    public boolean isWannTun() {
        final int weekDay = myanmarDate.getWeekDayValue();
        final int mmonth = myanmarDate.getMonth();
        if ((shanMonth == 5 || shanMonth == 1) && (weekDay == 2 || weekDay == 6)) {
            return true;
        } else if ((shanMonth == 6 || shanMonth == 2 || shanMonth == 10) && (weekDay == 5 || weekDay == 0)) {
            return true;
        } else if ((shanMonth == 7 || shanMonth == 3 || shanMonth == 11) && (weekDay == 3 || weekDay == 5)) {
            return true;
        } else if ((shanMonth == 8 || shanMonth == 4) && (weekDay == 1 || weekDay == 4)) {
            // လိူၼ်ပႅတ်ႇ
            return true;
        } else {
            // လိူၼ်ၵဝ်ႈ & လိူၼ်သိပ်းသွင်
            return (shanMonth == 9 || mmonth == 12) && (weekDay == 4 || weekDay == 6);
        }
    }

    public String getWannTun() {
        return isWannTun() ? "ဝၼ်းထုၼ်း" : "";
    }

    public boolean isWannPyaat() {
        final int weekDay = myanmarDate.getWeekDayValue();
        switch (shanMonth) {
            case 6:
            case 10:
            case 2:
                return (weekDay == 4 || weekDay == 6);
            case 1:
            case 5:
            case 9:
                return (weekDay == 5 || weekDay == 0);
            case 3:
            case 7:
            case 11:
                return (weekDay == 1 || weekDay == 2);
            case 4:
            case 8:
            case 12:
                return (weekDay == 3 || weekDay == 7);
            default:
                return false;
        }
    }

    public String getWannPyaat() {
        return isWannPyaat() ? "ဝၼ်းပျၢတ်ႈ" : "";
    }

    // ဝၼ်းၸူမ်
    public boolean isWannJum() {
        int weekDay = myanmarDate.getWeekDayValue();
        return ((shanMonth == 1 || shanMonth == 8) && (weekDay == 2)) ||
                ((shanMonth == 2 || shanMonth == 9 || shanMonth == 11) && (weekDay == 3)) ||
                ((shanMonth == 3 || shanMonth == 10) && (weekDay == 4)) ||
                ((shanMonth == 4) && (weekDay == 5)) ||
                ((shanMonth == 5 || shanMonth == 12) && (weekDay == 6)) ||
                ((shanMonth == 6) && (weekDay == 0)) ||
                ((shanMonth == 7) && (weekDay == 1));
    }

    public String getWannJum() {
        return isWannJum() ? "ဝၼ်းၸူမ်" : "";
    }

    public boolean isWannPhoo() {
        int weekDay = myanmarDate.getWeekDayValue();
        return (shanMonth == 1 || shanMonth == 8) && (weekDay == 6) ||
                (shanMonth == 2) && (weekDay == 0) ||
                (shanMonth == 3) && (weekDay == 1) ||
                (shanMonth == 4 || shanMonth == 9) && (weekDay == 2) ||
                (shanMonth == 5 || shanMonth == 10) && (weekDay == 3) ||
                (shanMonth == 6 || shanMonth == 11) && (weekDay == 4) ||
                (shanMonth == 7 || shanMonth == 12) && (weekDay == 1);
    }

    public String getWannPhoo() {
        return isWannPhoo() ? "ဝၼ်းၽူး" : "";
    }

    public boolean isMweLone() {
        int fortnightDayValue = myanmarDate.getFortnightDayValue();
        int adjustedWeekDayValue = (myanmarDate.getWeekDayValue() == 0) ? 7 : myanmarDate.getWeekDayValue();
        return (fortnightDayValue + adjustedWeekDayValue) == 13;
    }

    public String getMweLone() {
        return (isMweLone()) ? "မူၺ်ႉလူင်" : "";
    }

    public boolean isWannNao() {
        final int fortnightDay = myanmarDate.getFortnightDayValue();
        final int weekDay = myanmarDate.getWeekDayValue();
        return (fortnightDay == 1 && weekDay == 1) ||
                (fortnightDay == 4 && weekDay == 2) ||
                (fortnightDay == 6 && weekDay == 3) ||
                (fortnightDay == 9 && weekDay == 4) ||
                (fortnightDay == 8 && weekDay == 5) ||
                (fortnightDay == 7 && weekDay == 6) ||
                (fortnightDay == 8 && weekDay == 0);
    }

    public String getWannNao() {
        return isWannNao() ? "ဝၼ်းၼဝ်ႈ" : "";
    }

    public String getHoNagaa() {
        if (shanMonth >= 1 && shanMonth <= 3) return "ဝၢႆႇတၢင်းၸၢၼ်း";
        if (shanMonth >= 4 && shanMonth <= 6) return "ဝၢႆႇတၢင်းတူၵ်း";
        if (shanMonth >= 7 && shanMonth <= 9) return "ဝၢႆႇတၢင်းႁွင်ႇ";
        if (shanMonth >= 10 && shanMonth <= 12) return "ဝၢႆႇတၢင်းဢွၵ်ႇ";
        return "";
    }


    public String getWannMwe() {
        return "မူၺ်ႉ" + WANN_MWE[myanmarDate.getDayOfMonth() - 1];
    }

    public String getPheeKin() {
        return "ၽီၵိၼ်" + WANN_PHEE_KIN[myanmarDate.getDayOfMonth() - 1];
    }

    /***************************** ဝၼ်းတႆးလီဢိၵ်ႇၸႃႉ ႁေႃးၸွမ်းမႄႈပီႈ ****************************/

    // သေတင်ႈ
    public boolean isSayTang() {
        if ((shanMonth == 1 || shanMonth == 7) && (mePee == 2)) {
            return true;
        } else if ((shanMonth == 2 || shanMonth == 5 || shanMonth == 11 || shanMonth == 8) && (mePee == 3)) {
            return true;
        } else if ((shanMonth == 3 || shanMonth == 6 || shanMonth == 12 || shanMonth == 9) && (mePee == 7)) {
            return true;
        } else return (shanMonth == 4 || shanMonth == 10) && (mePee == 4);
    }

    // သေႁိပ်ႈ
    public boolean isSayHip() {
        return ((shanMonth == 1 || shanMonth == 7) && (mePee == 3)) ||
                ((shanMonth == 2 || shanMonth == 5 || shanMonth == 8 || shanMonth == 11) && (mePee == 4)) ||
                ((shanMonth == 3 || shanMonth == 6 || shanMonth == 9 || shanMonth == 12) && (mePee == 8)) ||
                (shanMonth == 4 || shanMonth == 10) && (mePee == 5);
    }

    // သေၸွမ်း
    public boolean isSayJom() {
        return (shanMonth == 1 || shanMonth == 7) && mePee == 4 ||
                (shanMonth == 2 || shanMonth == 5 || shanMonth == 11 || shanMonth == 8) && mePee == 5 ||
                (shanMonth == 3 || shanMonth == 6 || shanMonth == 12 || shanMonth == 9) && mePee == 9 ||
                (shanMonth == 4 || shanMonth == 10) && mePee == 6;
    }

    // သေယမ်
    public boolean isSayYam() {
        return (shanMonth == 1 || shanMonth == 7) && mePee == 7 ||
                (shanMonth == 2 || shanMonth == 5 || shanMonth == 11 || shanMonth == 8) && mePee == 8 ||
                (shanMonth == 3 || shanMonth == 6 || shanMonth == 12 || shanMonth == 9) && mePee == 4 ||
                (shanMonth == 4 || shanMonth == 10) && mePee == 9;
    }

    // ဝၼ်းႁၢမ်းဝၼ်းၵႅၼ်
    public boolean isWannHarmWannKyan() {
        switch (shanMonth) {
            case 1:
            case 6:
            case 11:
                return mePee == 4;
            case 2:
            case 7:
            case 12:
                return mePee == 2;
            case 3:
            case 8:
                return mePee == 5;
            case 4:
            case 9:
                return mePee == 8;
            case 5:
            case 10:
                return mePee == 6;
            default:
                return false;
        }
    }

    /************** ဝၼ်းတႆးလီဢိၵ်ႇၸႃႉ ႁေႃးၸွမ်းလုၵ်ႈပီႈ ******************/

    // ၸူမ်တိၼ်သိူဝ် - ၵမ်ၵႂႃႇတိုၵ်ႉ
    public boolean isJomTinSur() {
        return (shanMonth == 1 || shanMonth == 5 || shanMonth == 9) && lukPee == 0 ||
                (shanMonth == 2 || shanMonth == 6 || shanMonth == 10) && lukPee == 1 ||
                (shanMonth == 3 || shanMonth == 7 || shanMonth == 11) && lukPee == 6 ||
                (shanMonth == 4 || shanMonth == 8 || shanMonth == 12) && lukPee == 9;
    }

    // ၵမ်သိုဝ်ႉၶၢႆဢမ်ႇလီ
    public boolean isKamSiuKhai() {
        switch (shanMonth) {
            case 1:
                return lukPee == 2;
            case 2:
                return lukPee == 4;
            case 3:
            case 12:
                return lukPee == 1;
            case 4:
                return lukPee == 3;
            case 5:
                return lukPee == 0;
            case 6:
            case 9:
                return lukPee == 8;
            case 7:
            case 8:
            case 10:
                return lukPee == 7;
            case 11:
                return lukPee == 6;
            default:
                return false;
        }
    }

    // ၵမ်ယဵပ်ႉၶူဝ်းလဵင်းၼုင်ႈ
    public boolean isKamYeipKho() {
        return ((shanMonth == 1 && lukPee == 1)
                || ((shanMonth == 2 || shanMonth == 6 || shanMonth == 11) && lukPee == 3)
                || (shanMonth == 3 && lukPee == 8)
                || ((shanMonth == 4 || shanMonth == 10) && lukPee == 4)
                || ((shanMonth == 5 || shanMonth == 12) && lukPee == 2)
                || (shanMonth == 7 && lukPee == 7)
                || (shanMonth == 8 && lukPee == 6)
                || (shanMonth == 9 && lukPee == 5));
    }

    // ဝၼ်းမူၺ်ႉ - ၵမ်ၵူပ်ႉၵူႈသွမ်ႉမိင်ႈ
    public boolean isWannMwe() {
        switch (shanMonth) {
            case 1:
                return lukPee == 2;
            case 2:
                return lukPee == 4;
            case 3:
            case 6:
                return lukPee == 6;
            case 4:
            case 8:
                return lukPee == 8;
            case 5:
            case 11:
                return lukPee == 11;
            case 7:
            case 10:
                return lukPee == 3;
            case 9:
                return lukPee == 5;
            case 12:
                return lukPee == 0;
            default:
                return false;
        }
    }

    // ၵမ်ၶဵၼ်ႇႁွၵ်ႇလၢပ်ႇ
    public boolean isKamKhenHogLep() {
        switch (shanMonth) {
            case 1:
            case 5:
            case 9:
                return (lukPee == 11);
            case 2:
            case 6:
            case 10:
                return (lukPee == 2);
            case 3:
            case 7:
            case 11:
                return (lukPee == 5);
            case 4:
            case 8:
            case 12:
                return (lukPee == 8);
            default:
                return false;
        }
    }

    // ၵမ်ဝၢၼ်ႇၵႃႈ
    public boolean isKamWaanKaa() {
        switch (shanMonth) {
            case 1:
            case 5:
            case 9:
                return lukPee == 3;
            case 2:
            case 6:
            case 10:
                return lukPee == 6;
            case 3:
            case 7:
            case 11:
                return lukPee == 9;
            case 4:
            case 8:
            case 12:
                return lukPee == 0;
            default:
                return false;
        }
    }

    // မူၺ်ႉၶွမ်ႉ
    public boolean isMweKhom() {
        switch (shanMonth) {
            case 1:
            case 5:
            case 9:
                return lukPee == 0;
            case 2:
            case 6:
            case 10:
                return lukPee == 1;
            case 3:
            case 7:
            case 11:
                return lukPee == 4;
            case 4:
            case 8:
            case 12:
                return lukPee == 3;
            default:
                return false;
        }
    }

    // ၵမ်ဢဝ်ၽူဝ်မေး
    public boolean KamAoPhoMay() {
        switch (shanMonth) {
            case 1:
            case 5:
            case 9:
                return lukPee == 5;
            case 2:
            case 6:
            case 10:
                return lukPee == 9;
            case 3:
            case 7:
            case 11:
                return lukPee == 0;
            case 4:
            case 8:
            case 12:
                return lukPee == 3;
            default:
                return false;
        }
    }

    // ဝၼ်းလီႁဵတ်ႉႁႆႊၼႃး
    public boolean isWannLeeHeitHaiNaa() {
        switch (shanMonth) {
            case 1:
            case 5:
            case 9:
                return lukPee == 10;
            case 2:
            case 6:
            case 10:
                return lukPee == 1;
            case 3:
            case 7:
            case 11:
                return lukPee == 4;
            case 4:
            case 8:
            case 12:
                return lukPee == 7;
            default:
                return false;
        }
    }

    // ဢမ်ႇလီႁဵတ်ႉလွၵ်းႁႆႊၼႃး
    public boolean isUmLeeHeitHaiNaa() {
        return (shanMonth == lukPee) || (shanMonth == 12 && lukPee == 0);
    }

    // မူၺ်ႉၶႃးလိူင် - ၵမ်ပူၵ်းသဝ်
    public boolean isMweKharLurng() {
        return (shanMonth == lukPee + 1) ||
                (shanMonth == 11 && lukPee == 0) ||
                (shanMonth == 12 && lukPee == 1);
    }

    // မူၺ်ႉၶႃးလိူင် - ၵမ်ယူတ်းၶႃး
    public boolean isMweKharLurng2() {
        return (shanMonth == 1 && lukPee == 4) ||
                (shanMonth == 2 && lukPee == 5) ||
                (shanMonth == 3 && lukPee == 6) ||
                (shanMonth == 4 && lukPee == 7) ||
                (shanMonth == 5 && lukPee == 8) ||
                (shanMonth == 6 && lukPee == 9) ||
                (shanMonth == 7 && lukPee == 10) ||
                (shanMonth == 8 && lukPee == 11) ||
                (shanMonth == 9 && lukPee == 0) ||
                (shanMonth == 10 && lukPee == 1) ||
                (shanMonth == 11 && lukPee == 2) ||
                (shanMonth == 12 && lukPee == 3);
    }

    // ၵမ်ၵၢႆႇၶူဝ်လႆ
    public boolean isKamKaaiKhoLai() {
        return (shanMonth == 1 && lukPee == 8) ||
                (shanMonth == 2 && lukPee == 9) ||
                (shanMonth == 3 && lukPee == 10) ||
                (shanMonth == 4 && lukPee == 11) ||
                (shanMonth == 5 && lukPee == 0) ||
                (shanMonth == 6 && lukPee == 1) ||
                (shanMonth == 7 && lukPee == 2) ||
                (shanMonth == 8 && lukPee == 3) ||
                (shanMonth == 9 && lukPee == 4) ||
                (shanMonth == 10 && lukPee == 5) ||
                (shanMonth == 11 && lukPee == 6) ||
                (shanMonth == 12 && lukPee == 7);
    }

    // ႁိူဝ်ႇလႆ - ၵမ်ႁဵတ်းႁိူၼ်း
    public boolean isHoeLai() {
        return (shanMonth == 1 && lukPee == 7) ||
                (shanMonth == 2 && lukPee == 8) ||
                (shanMonth == 3 && lukPee == 9) ||
                (shanMonth == 4 && lukPee == 10) ||
                (shanMonth == 5 && lukPee == 11) ||
                (shanMonth == 6 && lukPee == 0) ||
                (shanMonth == 7 && lukPee == 1) ||
                (shanMonth == 8 && lukPee == 2) ||
                (shanMonth == 9 && lukPee == 3) ||
                (shanMonth == 10 && lukPee == 4) ||
                (shanMonth == 11 && lukPee == 5) ||
                (shanMonth == 12 && lukPee == 6);
    }

    // ဝၼ်းႁဝ်ၶၢႆ - ၵမ်ႁပ်ႉၸိူဝ်ႉ
    public boolean isWannHaoKhaai() {
        return (shanMonth == 1 && lukPee == 0) ||
                (shanMonth == 2 && lukPee == 11) ||
                (shanMonth == 3 && lukPee == 10) ||
                (shanMonth == 4 && lukPee == 9) ||
                (shanMonth == 5 && lukPee == 8) ||
                (shanMonth == 6 && lukPee == 7) ||
                (shanMonth == 7 && lukPee == 6) ||
                (shanMonth == 8 && lukPee == 5) ||
                (shanMonth == 9 && lukPee == 4) ||
                (shanMonth == 10 && lukPee == 3) ||
                (shanMonth == 11 && lukPee == 2) ||
                (shanMonth == 12 && lukPee == 1);
    }

    // ဝၼ်းႁဝ်တၢႆ - ၵမ်သိုဝ်ႉဝူဝ်းၵႂၢႆး
    public boolean isWannHaoTaai() {
        return (shanMonth == 1 && lukPee == 1) ||
                (shanMonth == 2 && lukPee == 0) ||
                (shanMonth == 3 && lukPee == 11) ||
                (shanMonth == 4 && lukPee == 10) ||
                (shanMonth == 5 && lukPee == 9) ||
                (shanMonth == 6 && lukPee == 8) ||
                (shanMonth == 7 && lukPee == 7) ||
                (shanMonth == 8 && lukPee == 6) ||
                (shanMonth == 9 && lukPee == 5) ||
                (shanMonth == 10 && lukPee == 4) ||
                (shanMonth == 11 && lukPee == 3) ||
                (shanMonth == 12 && lukPee == 2);
    }

    // တုမ်မူၺ်ႉ - ၵမ်ၶူတ်ႉလိၼ်
    // todo check logic same code as isWannHaoTaai
    public boolean isTumMway() {
        return (shanMonth == 1 && lukPee == 1) ||
                (shanMonth == 2 && lukPee == 0) ||
                (shanMonth == 3 && lukPee == 11) ||
                (shanMonth == 4 && lukPee == 10) ||
                (shanMonth == 5 && lukPee == 9) ||
                (shanMonth == 6 && lukPee == 8) ||
                (shanMonth == 7 && lukPee == 7) ||
                (shanMonth == 8 && lukPee == 6) ||
                (shanMonth == 9 && lukPee == 5) ||
                (shanMonth == 10 && lukPee == 4) ||
                (shanMonth == 11 && lukPee == 3) ||
                (shanMonth == 12 && lukPee == 2);
    }


    // မူၺ်ႉၵဝ်ႈၵွင်
    public boolean isMweKaoKong() {
        switch (shanMonth) {
            case 1:
            case 5:
                return lukPee == 0;
            case 2:
            case 7:
                return lukPee == 11;
            case 3:
            case 12:
                return lukPee == 7;
            case 4:
            case 9:
            case 10:
                return lukPee == 2;
            case 6:
            case 8:
                return lukPee == 10;
            case 11:
                return lukPee == 6;
            default:
                return false;
        }
    }

    // ဝၼ်းၵျၢမ်းလူင်
    public boolean isWannKyamLone() {
        int day = myanmarDate.getFortnightDayValue();
        return (shanMonth == 2 || shanMonth == 3) && day == 4 ||
                (shanMonth == 4 || shanMonth == 5) && day == 5 ||
                (shanMonth == 6 || shanMonth == 7) && day == 6 ||
                (shanMonth == 8 || shanMonth == 9) && day == 1 ||
                (shanMonth == 10 || shanMonth == 11) && day == 2 ||
                (shanMonth == 12 || shanMonth == 1) && day == 9;
    }

    // ဝၼ်းယုတ်ႈ
    public boolean isWannYut() {
        int fortnightDay = myanmarDate.getFortnightDayValue();
        return (shanMonth == 5 || shanMonth == 8) && fortnightDay == 6 ||
                (shanMonth == 6 || shanMonth == 3) && fortnightDay == 4 ||
                (shanMonth == 7 || shanMonth == 10) && fortnightDay == 8 ||
                (shanMonth == 9 || shanMonth == 12) && fortnightDay == 10 ||
                (shanMonth == 2 || shanMonth == 11) && fortnightDay == 12 ||
                (shanMonth == 1 || shanMonth == 4) && fortnightDay == 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isSayTang()) {
            sb.append("သေတင်ႈ").append("၊ ");
        }
        if (isSayHip()) {
            sb.append("သေႁိပ်ႈ").append("၊ ");
        }
        if (isSayJom()) {
            sb.append("သေၸွမ်း").append("၊ ");
        }
        if (isSayYam()) {
            sb.append("သေယမ်").append("၊ ");
        }
        if (isJomTinSur()) {
            sb.append("ၸူမ်တိၼ်သိူဝ်").append("၊ ");
        }

        sb.append(getPheeKin()).append("၊ ");
        sb.append(getWannMwe()).append("၊ ");

        if (isMweKhom()) {
            sb.append("မူၺ်ႉၶွမ်ႈ").append("၊ ");
        }
        if (isMweKharLurng()) {
            sb.append("မူၺ်ႉၶႃးလိူင်").append("၊ ");
        }
        if (isMweKharLurng2()) {
            sb.append("မူၺ်ႉၶႃးလိူင်").append("၊ ");
        }
        if (isMweKaoKong()) {
            sb.append("မူၺ်ႉၵဝ်ႈၵွင်").append("၊ ");
        }
        if (isMweLone()) {
            sb.append("မူၺ်ႉလူင်").append("၊ ");
        }

        if (!getWannTun().isEmpty()) {
            sb.append("ဝၼ်းထုၼ်း").append("၊ ");
        }
        if (!getWannPyaat().isEmpty()) {
            sb.append("ဝၼ်းပျၢတ်ႈ").append("၊ ");
        }
        if (isWannJum()) {
            sb.append("ဝၼ်းၸူမ်").append("၊ ");
        }
        if (isWannPhoo()) {
            sb.append("ဝၼ်းၾူး").append("၊ ");
        }
        if (isWannNao()) {
            sb.append("ဝၼ်းၼဝ်ႈ").append("၊ ");
        }
        if (isWannHarmWannKyan()) {
            sb.append("ဝၼ်းႁၢမ်း").append("၊ ");
        }
        if (isWannYut()) {
            sb.append("ဝၼ်းယုတ်ႈ").append("၊ ");
        }
        if (isWannKyamLone()) {
            sb.append("ဝၼ်းၵျၢမ်းလူင်").append("၊ ");
        }

        final UnaryOperator<StringBuilder> replaceLambda = input -> {
            StringBuilder result = new StringBuilder(input);
            if (result.toString().trim().endsWith("၊")) {
                result.replace(result.length() - 2, result.length(), "။");
            }
            return result;
        };

        sb = replaceLambda.apply(sb);

        sb.append("\n").append("ႁူဝ်ၼၵႃး ")
                .append(getHoNagaa()).append("။\n");

        long epochDay = myanmarDate.toMyanmarLocalDate().toEpochDay();

        if (calculateMePee(epochDay) == 2 || calculateMePee(epochDay) == 7) {
            sb.append("ဝၼ်းၵၢတ်ႇမိူင်း").append("၊ ");
        }

        Astro astro = Astro.of(myanmarDate);

        if (astro.isSabbath()) {
            sb.append(astro.getSabbath()).append("၊ ");
        }

        sb = replaceLambda.apply(sb);

        return sb.toString();
    }

}