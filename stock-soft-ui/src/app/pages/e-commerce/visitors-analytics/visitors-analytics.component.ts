import { Component, OnDestroy } from '@angular/core';
import { takeWhile } from 'rxjs/operators';
import { NbThemeService } from '@nebular/theme';
import { OutlineData, VisitorsAnalyticsData } from '../../../@core/data/visitors-analytics';
import { forkJoin } from 'rxjs';
import { DashboardService } from 'app/services/dashboard.service';


@Component({
  selector: 'ngx-ecommerce-visitors-analytics',
  styleUrls: ['./visitors-analytics.component.scss'],
  templateUrl: './visitors-analytics.component.html',
})
export class ECommerceVisitorsAnalyticsComponent implements OnDestroy {
  private alive = true;
  topGainers=[];
  pieChartValue: number;
  chartLegend: any;
  visitorsAnalyticsData: { innerLine: any[]; outerLine: any[]; };
  colorScheme = {
    domain: ['#ff5252','#6200EA','#CDDC39','#795548','#673AB7','#EC407A', '#9CCC65', '#388E3C', '#FFCA28','#BCAAA4']
  };
 
  view: any[] = [700, 400];
  
  legendPosition: string = 'right';
  constructor(private dashboardService: DashboardService) {
                this.getGain();
  }

 
  getGain() {
     this.dashboardService.getTopGainers().subscribe((dd: any[]) => {
      var d = [];
    //var dd= [{"id":"bcbef581-4ea4-11eb-801d-0a075ba8efd2","name":"BROWNS INVESTMENTS PLC","company":"BIL.N0000","gain":45551.31000000,"investment":348507.25,"deltaUp":true,"gainPresentage":13.07},{"id":"bcbef6a4-4ea4-11eb-801d-0a075ba8efd2","name":"EXPOLANKA HOLDINGS PLC","company":"EXPO.N0000","gain":39290.24000000,"investment":328741.12,"deltaUp":true,"gainPresentage":11.95},{"id":"bcbef742-4ea4-11eb-801d-0a075ba8efd2","name":"Singer Finance (Lanka) PLC","company":"SFIN.0000","gain":17324.22000000,"investment":218702.34,"deltaUp":true,"gainPresentage":7.92},{"id":"bcbef7d7-4ea4-11eb-801d-0a075ba8efd2","name":"Commercial Bank of Ceylon PLC","company":"COMB.N0000","gain":15020.08000000,"investment":168119.52,"deltaUp":true,"gainPresentage":8.93},{"id":"bcbef840-4ea4-11eb-801d-0a075ba8efd2","name":"PIRAMAL GLASS CEYLON PLC","company":"GLAS.N0000","gain":6649.22000000,"investment":40417.66,"deltaUp":true,"gainPresentage":16.45},{"id":"bcbef8bd-4ea4-11eb-801d-0a075ba8efd2","name":"HAYLEYS FABRIC PLC","company":"MGT.N0000","gain":5428.48000000,"investment":49548.80,"deltaUp":true,"gainPresentage":10.96},{"id":"bcbef90c-4ea4-11eb-801d-0a075ba8efd2","name":"HEMAS HOLDINGS PLC","company":"HHL.N0000","gain":5347.20000000,"investment":138028.80,"deltaUp":true,"gainPresentage":3.87}]
    dd.forEach(function (value) {
    
      var company = value.company;
      var gain = parseInt(value.gain.toString());
      
      var obj={"name":company , "value":gain};
      console.log(obj);
    
      d.push(obj)
     
     
    });
    this.topGainers = d;
     
    });
  
}

  ngOnDestroy() {
    this.alive = false;
  }
}
