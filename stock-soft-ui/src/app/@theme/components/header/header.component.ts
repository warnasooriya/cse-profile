import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';

import { UserData } from '../../../@core/data/users';
import { LayoutService } from '../../../@core/utils';
import { map, takeUntil } from 'rxjs/operators';
import { Observable, Subject } from 'rxjs';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from 'environments/environment';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  childMessage: any;
  private stompClient;
  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: any;
  asphChangedAmountCls = 'aspi-down-total-clas';
  aspiTrendPersentage = 'aspi-down nb-arrow-dropup';
  sandpTrendPersentage = 'aspi-up nb-arrow-dropup';
  sandpChangedAmountCls = 'aspi-up-total-clas  ';
  snpPre: any;
  snpAmount: any;
  aspiPre: any;
  aspiAmount: any;
  aspichange: any;
  snpchange: any;

  themes = [
    // {
    //   value: 'default',
    //   name: 'Light',
    // },
    {
      value: 'dark',
      name: 'Dark',
    }
    // ,
    // {
    //   value: 'cosmic',
    //   name: 'Cosmic',
    // },
    // {
    //   value: 'corporate',
    //   name: 'Corporate',
    // },
  ];

  currentTheme = 'dark';

  userMenu = [{ title: 'Profile', url: 'profile' }, { title: 'Log out', url: '#/auth/logout' }];

  constructor(private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private userService: UserData,
    private layoutService: LayoutService,
    private breakpointService: NbMediaBreakpointsService) {

    const ws = new SockJS(environment.socketUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/indexes', (message) => {
        if (message.body) {
          let indexObj = JSON.parse(message.body);
          this.snpPre = Number(indexObj.snpPersentage).toFixed(2);
          this.snpAmount = indexObj.snpAmount;
          this.aspiPre = Number(indexObj.aspiPersentage).toFixed(2);
          this.aspiAmount = indexObj.aspiAmount;

          this.aspichange = Number(indexObj.aspichange).toFixed(2);
          this.snpchange = Number(indexObj.snpchange).toFixed(2);

          if (indexObj.aspiPersentage <= 0) {
            this.asphChangedAmountCls = 'aspi-down-total-clas';
            this.aspiTrendPersentage = 'aspi-down nb-arrow-dropdown';
          } else {
            this.asphChangedAmountCls = 'aspi-up-total-clas';
            this.aspiTrendPersentage = 'aspi-up nb-arrow-dropup';
          }
          if (indexObj.snpPersentage <= 0) {
            this.sandpTrendPersentage = 'aspi-down nb-arrow-dropdown';
            this.sandpChangedAmountCls = 'aspi-down-total-clas';
          } else {
            this.sandpTrendPersentage = 'aspi-up nb-arrow-dropup';
            this.sandpChangedAmountCls = 'aspi-up-total-clas';
          }


          // this.updateEvents.next(JSON.parse(message.body));
        }
      });
    });
  }

  ngOnInit() {

    // localStorage.setItem("userId","402888e8759dfd9b01759dfdc66f0000");
    this.currentTheme = this.themeService.currentTheme;
    this.changeTheme('dark');
    this.user = { name: localStorage.getItem('csePUsername'), picture: localStorage.getItem('csePicture') }

    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);



  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}
