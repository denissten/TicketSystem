package gui;

import java.awt.*;

public class Utill {
    public static GridBagConstraints createGridBagConstrains(double weightx, double weighty, int gridx, int gridy,
                                                       int gridwidth, int gridheight, int ipadx, int ipady,
                                                       int fill){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.ipadx = ipadx;
        gridBagConstraints.ipady = ipady;
        gridBagConstraints.fill = fill;

        return gridBagConstraints;
    }

    public static boolean isNumber(String int_string){
        try {
            long i = Long.parseLong(int_string);
            return true;
        } catch (NumberFormatException exception){
            return false;
        }
    }

    public static boolean isNumberF(String float_string){
        try {
            float i = Float.parseFloat(float_string);
            return true;
        } catch (NumberFormatException exception){
            return false;
        }
    }

    public static String getStatusName(int i){
        return switch (i) {
            case 1 -> "Забронирован";
            case 2 -> "Не одобрен";
            case 3 -> "На рассмотрении";
            default -> "";
        };
    }

    public static String getRoleName(int i){
        return switch (i){
            case 1 -> "Пользователь";
            case 2 -> "Сотрудник";
            case 3 -> "Администратор";
            default -> "";
        };
    }

}
