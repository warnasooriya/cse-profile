import { Component, OnDestroy } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { DashboardService } from 'app/services/dashboard.service';
import { takeWhile } from 'rxjs/operators';

import { UserActivityData, UserActive } from '../../../@core/data/user-activity';

@Component({
  selector: 'ngx-user-activity',
  styleUrls: ['./user-activity.component.scss'],
  templateUrl: './user-activity.component.html',
})
export class ECommerceUserActivityComponent implements OnDestroy {

  private alive = true;

  userActivity: any = [];
  type = 'month';
  types = ['week', 'month', 'year'];
  currentTheme: string;

  constructor(private themeService: NbThemeService,
              private dashboardService: DashboardService) {
    this.themeService.getJsTheme()
      .pipe(takeWhile(() => this.alive))
      .subscribe(theme => {
        this.currentTheme = theme.name;
    });

    this.getTopGainers();
  }

  getTopGainers() {
       this.dashboardService.getTopGainers().subscribe((userActivityData: any[]) => {
        this.userActivity = userActivityData;
      });

       //this.userActivity= [{"id":"bcbef581-4ea4-11eb-801d-0a075ba8efd2","name":"BROWNS INVESTMENTS PLC","company":"BIL.N0000","gain":45551.31000000,"investment":348507.25,"deltaUp":true,"gainPresentage":13.07},{"id":"bcbef6a4-4ea4-11eb-801d-0a075ba8efd2","name":"EXPOLANKA HOLDINGS PLC","company":"EXPO.N0000","gain":39290.24000000,"investment":328741.12,"deltaUp":true,"gainPresentage":11.95},{"id":"bcbef742-4ea4-11eb-801d-0a075ba8efd2","name":"Singer Finance (Lanka) PLC","company":"SFIN.0000","gain":17324.22000000,"investment":218702.34,"deltaUp":true,"gainPresentage":7.92},{"id":"bcbef7d7-4ea4-11eb-801d-0a075ba8efd2","name":"Commercial Bank of Ceylon PLC","company":"COMB.N0000","gain":15020.08000000,"investment":168119.52,"deltaUp":true,"gainPresentage":8.93},{"id":"bcbef840-4ea4-11eb-801d-0a075ba8efd2","name":"PIRAMAL GLASS CEYLON PLC","company":"GLAS.N0000","gain":6649.22000000,"investment":40417.66,"deltaUp":true,"gainPresentage":16.45},{"id":"bcbef8bd-4ea4-11eb-801d-0a075ba8efd2","name":"HAYLEYS FABRIC PLC","company":"MGT.N0000","gain":5428.48000000,"investment":49548.80,"deltaUp":true,"gainPresentage":10.96},{"id":"bcbef90c-4ea4-11eb-801d-0a075ba8efd2","name":"HEMAS HOLDINGS PLC","company":"HHL.N0000","gain":5347.20000000,"investment":138028.80,"deltaUp":true,"gainPresentage":3.87}]
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
