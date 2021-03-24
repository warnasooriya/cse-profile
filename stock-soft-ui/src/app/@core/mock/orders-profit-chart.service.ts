import { of as observableOf,  Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OrdersChart, OrdersChartData } from '../data/orders-chart';
import { OrderProfitChartSummary, OrdersProfitChartData } from '../data/orders-profit-chart';
import { ProfitChart, ProfitChartData } from '../data/profit-chart';
import { DashboardService } from 'app/services/dashboard.service';

@Injectable()
export class OrdersProfitChartService extends OrdersProfitChartData {
 
  private summary = [
    {
      title: 'Total Investment',
      value: 3654,
    },
    {
      title: 'Current Month',
      value: 946,
    },
    
  ];

  constructor(private ordersChartService: OrdersChartData,
              private profitChartService: ProfitChartData,
              private dashboardService:DashboardService
                            
              ) {
    super();
  }

  getOrderProfitChartSummary(): Observable<OrderProfitChartSummary[]> {

    return observableOf(this.summary);
  }

 

  getProfitChartData(period: string): Observable<ProfitChart> {
    return observableOf(this.profitChartService.getProfitChartData(period));
  }
}
