/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class ProfitSummary {
    public final double grossSales;     // from transactions (Paid)
    public final double purchaseCost;   // from catch (purchases)
    public final double netSales;       // gross - cost
    public final javafx.scene.chart.XYChart.Series<String, Number> grossSeries;
    public final javafx.scene.chart.XYChart.Series<String, Number> costSeries;

    public ProfitSummary(double g, double c,
                         javafx.scene.chart.XYChart.Series<String, Number> gs,
                         javafx.scene.chart.XYChart.Series<String, Number> cs) {
        this.grossSales = g;
        this.purchaseCost = c;
        this.netSales = g - c;
        this.grossSeries = gs;
        this.costSeries = cs;
    }
}

