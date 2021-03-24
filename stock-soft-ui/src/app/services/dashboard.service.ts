import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import {  OrdersChart } from 'app/@core/data/orders-chart';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

   
  getProfitDetails(data: any) {
    let url = environment.endPointURL + "dashboard/getProfitDetails";
    return this.http.post(url,data);
  }
  

  getAccountSummary() {
   let url = environment.endPointURL + "dashboard/getAccountSummary/"+localStorage.getItem("userId");
    return this.http.get(url);
  }
  getTopGainers() {
    let url = environment.endPointURL + "dashboard/getTopGainers/"+localStorage.getItem("userId");
    return this.http.get(url);
  }
  

  getTotalEarnings() {
    let url = environment.endPointURL + "dashboard/getTotalEarnings/"+localStorage.getItem("userId");
    return this.http.get(url);
  }

  constructor(private http:HttpClient) {

   }

 
  getDataLabels(nPoints: number, labelsArray: string[]): string[] {
    const labelsArrayLength = labelsArray.length;
    const step = Math.round(nPoints / labelsArrayLength);

    return Array.from(Array(nPoints)).map((item, index) => {
      const dataIndex = Math.round(index / step);

      return index % step === 0 ? labelsArray[dataIndex] : '';
    });
  }

  getOrdersChartData(period) {
      let url = environment.endPointURL + "dashboard/getMonthlyDBSPCharts/"+localStorage.getItem("userId")+"/"+period;
      return this.http.get(url);
  }

  getInvestmentSummary() {
   let url = environment.endPointURL + "dashboard/getAllDepositAmount/"+localStorage.getItem("userId");
    return this.http.get(url);
      
  }
}
