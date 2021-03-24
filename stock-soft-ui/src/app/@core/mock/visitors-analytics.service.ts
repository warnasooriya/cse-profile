import { Injectable } from '@angular/core';
import { of as observableOf, Observable } from 'rxjs';
import { PeriodsService } from './periods.service';
import { OutlineData, VisitorsAnalyticsData } from '../data/visitors-analytics';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';

@Injectable()
export class VisitorsAnalyticsService extends VisitorsAnalyticsData {

  constructor(private periodService: PeriodsService,private http:HttpClient) {
    super();
  }

  private pieChartValue = 75;
  private innerLinePoints: number[] = [
    94, 188, 225, 244, 253, 254, 249, 235, 208,
    173, 141, 118,
  ];
  private outerLinePoints: number[] = [
    85, 71, 59, 50, 45, 42, 41, 44 , 58, 88,
    136 , 199,
  ];
  private generateOutlineLineData(): OutlineData[] {
    const months = this.periodService.getMonths();
    const outerLinePointsLength = this.outerLinePoints.length;
    const monthsLength = months.length;

    return this.outerLinePoints.map((p, index) => {
      const monthIndex = Math.round(index );
      const label = months[monthIndex];

      return {
        label,
        value: p,
      };
    });
  }

  getInnerLineChartData(userId): Observable<any[]> {
      // let url = environment.endPointURL + "dashboard/getMonthlyDBSPCharts/"+userId+"/month";
      // return this.http.get<any[]>(url);
   return observableOf(this.innerLinePoints);
  }

  getOutlineLineChartData(): Observable<OutlineData[]> {
    return observableOf(this.generateOutlineLineData());
  }

  getPieChartData(): Observable<number> {
    return observableOf(this.pieChartValue);
  }
}
