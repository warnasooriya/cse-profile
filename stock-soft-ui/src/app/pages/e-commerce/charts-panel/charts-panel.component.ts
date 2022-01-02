import { Component, OnDestroy, ViewChild } from '@angular/core';
import { takeWhile } from 'rxjs/operators';

import { OrdersChartComponent } from './charts/orders-chart.component';
import { ProfitChartComponent } from './charts/profit-chart.component';
import { OrdersProfitChartData } from '../../../@core/data/orders-profit-chart';
import { DashboardService } from 'app/services/dashboard.service';

@Component({
  selector: 'ngx-ecommerce-charts',
  styleUrls: ['./charts-panel.component.scss'],
  templateUrl: './charts-panel.component.html',
})
export class ECommerceChartsPanelComponent implements OnDestroy {

  private alive = true;

  chartPanelSummary = [];
  period: string = 'month';
  ordersChartData: any;
  profitChartData: any;

  @ViewChild('profitChart', { static: true }) profitChart: ProfitChartComponent;
  @ViewChild('ordersChart', { static: true }) ordersChart: OrdersChartComponent;


  constructor(private ordersProfitChartService: OrdersProfitChartData, private dashboardService: DashboardService) {
    this.dashboardService.getAccountSummary().subscribe((sumData: any[]) => {
      this.chartPanelSummary = [
        {
          title: 'Total Investment',
          value: Number(sumData['totalInvesment']),
          subtitle: sumData['totalInvesmentInWord']
        },
        {
          title: 'Total Profit',
          value: Number(sumData['totalProfit']),
          subtitle: sumData['totalProfitInWord']
        }, {
          title: 'ROI %',
          value: Number(sumData['totalProfit']) / Number(sumData['totalInvesment']) * 100,
          subtitle: 'Return on Investment'
        },
        {
          title: 'Cash In Hand',
          value: Number(sumData['cashInHand']),
          subtitle: sumData['cashInHandInWord']
        },
        {
          title: 'Networth',
          value: Number(sumData['netWorth']),
          subtitle: sumData['netWorthInWord']
        },
        {
          title: 'Unrealized NW',
          value: Number(sumData['unrealizeNw']),
          subtitle: sumData['unrealizeNwInWord']
        },

      ];

    });

    // this.chartPanelSummary = [
    //   {
    //     title: 'Total Investment',
    //     value: 5000.00,
    //   },
    //   {
    //     title: 'Total Profit',
    //     value: 1500,
    //   },
    //   {
    //     title: 'Cash In Hand',
    //     value: 25000,
    //   },
    //   {
    //     title: 'Networth',
    //     value: 75000,
    //   },

    // ];

    this.getOrdersChartData(this.period);
    this.getProfitChartData(this.period);
  }

  setPeriodAndGetChartData(value: string): void {
    if (this.period !== value) {
      this.period = value;
    }

    this.getOrdersChartData(value);
    this.getProfitChartData(value);
  }

  changeTab(selectedTab) {
    if (selectedTab.tabTitle === 'Profit') {
      this.profitChart.resizeChart();
    } else {
      this.ordersChart.resizeChart();
    }
  }

  getOrdersChartData(period: string) {
    this.dashboardService.getOrdersChartData(period).subscribe((ordersChartData: any) => {
      var lineDatas = [];
      lineDatas.push(ordersChartData["deposit"]);
      lineDatas.push(ordersChartData["buy"]);
      lineDatas.push(ordersChartData["sell"]);
      lineDatas.push(ordersChartData["profit"]);
      var objD = {
        chartLabel: ordersChartData["labels"],
        linesData: lineDatas
      }
      this.ordersChartData = objD;
      // console.log(this.ordersChartData);
    });
    //  var ordersChartDataD={"labels":["2020-Feb","2020-Mar","2020-Apr","2020-May","2020-Jun","2020-Jul","2020-Aug","2020-Sep","2020-Oct","2020-Nov","2020-Dec","2021-Jan"],"deposit":[0.00,0.00,0.00,0.00,95000.00,0.00,0.00,90000.00,40000.00,400500.00,200000.00,0.00],"buy":[0.00,0.00,0.00,0.00,94403.04,0.00,101767.17,91515.11,552.12,766135.68,958182.78,362120.83],"sell":[0.00,0.00,0.00,0.00,0.00,0.00,103244.56,0.00,0.00,325908.48,764957.43,386902.61],"profit":[0.00,0.00,0.00,0.00,0.00,0.00,8841.52,0.00,0.00,28646.02,74482.57,39504.84]};
    //   var lineDatas=[];
    //   lineDatas.push(ordersChartDataD["deposit"]);
    //   lineDatas.push(ordersChartDataD["buy"]);
    //   lineDatas.push(ordersChartDataD["sell"]);
    //   lineDatas.push(ordersChartDataD["profit"]);
    //   var objD = {
    //     chartLabel:ordersChartDataD["labels"],
    //     linesData:lineDatas
    //    }
    //     this.ordersChartData = objD;

  }

  getProfitChartData(period: string) {
    // this.ordersProfitChartService.getProfitChartData(period)
    //   .pipe(takeWhile(() => this.alive))
    //   .subscribe(profitChartData => {
    //     this.profitChartData = profitChartData;
    //     console.log(this.profitChartData);
    //   });
    this.dashboardService.getOrdersChartData(period).subscribe((ptd: any) => {
      var lineDatas = [];
      lineDatas.push(ptd["deposit"]);
      lineDatas.push(ptd["buy"]);
      lineDatas.push(ptd["sell"]);
      lineDatas.push(ptd["profit"]);
      var objD = {
        chartLabel: ptd["labels"],
        data: lineDatas
      }
      this.profitChartData = objD;
      //console.log(this.profitChartData);
    });
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
