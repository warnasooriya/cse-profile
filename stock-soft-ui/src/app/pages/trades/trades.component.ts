import { ChangeDetectionStrategy, Component, Input, ViewChild, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { NbSortDirection, NbSortRequest, NbTreeGridDataSource, NbTreeGridDataSourceBuilder, NbWindowService, NbDateService } from '@nebular/theme';
import { Observable, of, Subject } from 'rxjs';
import { map, startWith, takeWhile } from 'rxjs/operators';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CompanyService } from 'app/services/company.service';
import { BuyService } from 'app/services/buy.service';
import Swal from 'sweetalert2'
import { LocalDataSource } from 'ng2-smart-table';
import { SmartTableData } from 'app/@core/data/smart-table';
import { SellService } from 'app/services/sell.service';
import { DashboardService } from 'app/services/dashboard.service';
import { DepositService } from 'app/services/deposit.service';
import { parse } from 'path';
import { LayoutService } from 'app/@core/utils';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from 'environments/environment';

interface TreeNode<T> {
  data: T;
  children?: TreeNode<T>[];
  expanded?: boolean;
}

interface FSEntry {
  kind: String;
  company: string;
  qty: number;
  avgPrice: number;
  charges: number;
  amount: number;
  date: String;
  //lotNumbers:String;

}

@Component({
  selector: 'ngx-trades',
  // changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './trades.component.html',
  styleUrls: ['./trades.component.scss']
})
export class TradesComponent implements OnInit {
  colorScheme = {
    backgroundColor: ['#ff5252', '#6200EA', '#CDDC39', '#795548', '#673AB7', '#EC407A', '#9CCC65', '#388E3C', '#FFCA28', '#BCAAA4']
  };
  totalDepositWords = "";
  submittedBuy = false;
  submittedSell = false;
  totalEquityHoldings = "0.00";
  totalEarnings = "0.00";
  currentMonthEarnings = "0.00"
  todayEarnings = "0.00";
  totalDeposits = "0.00";

  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-arrow-retweet">SALE</i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-paper-plane" nbTooltip="SALE" nbTooltipIcon="alert-triangle" nbTooltipStatus="danger" title="SALE" ></i>',
      confirmDelete: true,
    },
    actions: {
      columnTitle: ':::',
      add: false,
      edit: false,
      delete: true,
    },
    columns: {
      code: {
        title: 'Company',
        type: 'string',
      },
      date: {
        title: 'Purchase Date',
        type: 'string',
        filter: false

      },
      qty: {
        title: 'Qty',
        type: 'number',
        filter: false
      },
      price: {
        title: 'Purchase Price',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      chargers: {
        title: 'Chargers',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      avgPrice: {
        title: 'Avarage Price',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      currentPrice: {
        title: 'Current Price',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      amount: {
        title: 'Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false
      },
      unrealizeProfit: {
        title: 'Profit',
        type: 'html',
        valuePrepareFunction: function (value) {

          if (value > 0) {
            return '<div  align="right" color="red" class="cell_success"> ' + Number(value).toFixed(2) + ' </div>';
          } else {
            return '<div  align="right" color="red" class="cell_danger"> ' + Number(value).toFixed(2) + ' </div>';
          }
        },
        filter: false
      },

      percentage: {
        title: 'Profit %',
        type: 'html',
        valuePrepareFunction: function (value) {

          if (value > 0) {
            return '<div  align="right" color="red" class="cell_success"> ' + Number(value).toFixed(2) + ' % </div>';
          } else {
            return '<div  align="right" color="red" class="cell_danger"> ' + Number(value).toFixed(2) + ' %  </div>';
          }
        },
        filter: false
      },


    },

  };



  settingsSell = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-arrow-retweet">SALE</i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-paper-plane" nbTooltip="SALE" nbTooltipIcon="alert-triangle" nbTooltipStatus="danger" title="SALE" ></i>',
      confirmDelete: true,
    },
    actions: {
      add: false,
      edit: false,
      delete: false,
    },
    pager: {
      display: true,
      perPage: 2
    },
    columns: {

      date: {
        title: 'Date',
        type: 'string',
        filter: false
      },
      qty: {
        title: 'Qty',
        type: 'number',
        filter: false
      },

      price: {
        title: 'Price',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      amount: {
        title: 'Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false
      },
    },
  };



  source: LocalDataSource;
  sellSource: LocalDataSource;

  myDate = new Date();
  cDate = "";
  constructor(
    private windowService: NbWindowService,
    protected dateService: NbDateService<Date>,
    private companyService: CompanyService,
    private buyService: BuyService,
    private formBuilder: FormBuilder,
    private sellService: SellService,
    private dashboardService: DashboardService,
    private depositService: DepositService,
    private layoutService: LayoutService,
    private datePipe: DatePipe
  ) {

    let dateString = this.datePipe.transform(this.myDate, 'yyyy-MM-dd');
    console.log('formatted Date', this.cDate);
    this.min = this.dateService.addDay(this.dateService.today(), -5);
    this.max = this.dateService.addDay(this.dateService.today(), 5);
    this.layoutService.onSafeChangeLayoutSize()
      .pipe(
        takeWhile(() => this.alive),
      )
      .subscribe(() => this.resizeChart());

    this.loadDataTable();


    const ws = new SockJS(environment.socketUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/trade', (message) => {
        if (message.body) {
          let newDataSet = [];
          let tradeObj = JSON.parse(message.body);

          let totalProfitLoss = 0.00;
          let totalQty = 0.00;
          let avgPrecentage = 0.00;
          let totalCharges = 0.00;
          let totalAmounts = 0.0;

          let length = this.availableStock.length;

          this.availableStock.forEach(function (com, inx) {
            let comCode = com.code;
            let foundCom = tradeObj.find(element => element.code == comCode);

            if (foundCom != undefined && foundCom != null && foundCom != "") {

              com.currentPrice = foundCom.price;
              let qty = Number(com.qty);
              let pdate = com.date;
              let commission = Number(com.commission);
              let commAmount = 0;
              let amountVal = qty * com.currentPrice;
              let costAmount = com.amount;

              if (dateString != pdate) {
                commAmount = (amountVal / 100) * commission;
              }
              let cValuation = Number(amountVal) - Number(commAmount);
              let profit = Number(cValuation) - Number(costAmount);
              let presentage = Number(profit) / (Number(costAmount) / 100);
              com.percentage = presentage;
              com.unrealizeProfit = profit;
              totalProfitLoss = totalProfitLoss + profit;
              avgPrecentage = avgPrecentage + presentage;
              totalQty = totalQty + qty;
              totalCharges = totalCharges + commAmount;
              totalAmounts = totalAmounts + costAmount;


            }

            if (comCode == ' ### TOTAL ### ') {

              com.chargers = totalCharges;
              com.amount = totalAmounts;
              com.unrealizeProfit = totalProfitLoss;
              com.percentage = (avgPrecentage / length);

            }
            newDataSet.push(com);
          });
          this.availableStock = newDataSet;
          //console.table(this.availableStock);
        }
      });
    });
    // setInterval(() => {
    //   this.loadDataTable();
    // }, 60000);

  }
  buyForm: FormGroup;
  sellForm: FormGroup;

  [x: string]: any;
  @ViewChild('autoInput') input;
  companyData: any;
  availableStock: any;
  availableLots: any;


  filteredControlOptions$: Observable<any[]>;
  filteredNgModelOptions$: Observable<any[]>;
  inputFormControl: FormControl;
  value: string;
  selectedCompany: any;
  tradeCommision: Number;

  // chart
  options: any = {};
  // end chart
  ngOnInit() {


    this.sellForm = this.formBuilder.group({
      company: ['', Validators.required],
      transDate: ['', Validators.required],
      price: ['', Validators.required],
      userId: ['', Validators.required],
      qty: ['', Validators.required],
      amount: ['', Validators.required],
    });


    this.buyForm = this.formBuilder.group({
      company: ['', Validators.required],
      transDate: ['', Validators.required],
      price: ['', Validators.required],
      qty: ['', Validators.required],
      amount: '',
      commission: '',
      netAmount: ''
    });

    this.getTotalValuesToCards();
    this.loadPreviousDepositTotals();
    this.companyService.getCompanies().subscribe((company: any[]) => {
      this.companyData = company;
    });


    this.companyService.getCommission().subscribe((commission: any[]) => {
      this.tradeCommision = commission[0]['commissionAmount'];
    });

    this.filteredControlOptions$ = of(this.companyData);
    this.filteredNgModelOptions$ = of(this.companyData);
    this.inputFormControl = new FormControl();
    this.filteredControlOptions$ = this.inputFormControl.valueChanges
      .pipe(
        startWith(''),
        map(filterString => this.filter(filterString)),
      );





  }

  ngOnChanges(): void {

    this.loadDataTable();

  }

  loadDataTable() {
    var availableStockList = [];
    this.buyService.getAvailableStock().subscribe((asl: any[]) => {
      asl.sort((a, b) => (a.percentage * -1) - (b.percentage * -1));

      this.availableStock = asl;
      asl.forEach(function (company) {
        var amount = company['amount'];
        var availableStockObj = { "name": company['code'], "value": amount };
        availableStockList.push(availableStockObj);


      });



      let totalProfitLoss = 0.00;
      let totalQty = 0.00;
      let avgPrecentage = 0.00;
      let totalCharges = 0.00;
      let totalAmounts = 0.0;

      this.availableStock.forEach(function (com) {
        let qty = Number(com.qty);
        let pdate = com.date;
        let commission = Number(com.chargers);
        let commAmount = 0;
        let costAmount = com.amount;
        totalProfitLoss = totalProfitLoss + com.unrealizeProfit;
        avgPrecentage = avgPrecentage + com.percentage;
        totalQty = totalQty + qty;
        totalCharges = totalCharges + commission;
        totalAmounts = totalAmounts + costAmount;
      });


      this.availableStock.push(
        {
          code: ' ### TOTAL ### ',
          date: '',
          qty: '',
          price: '',
          chargers: totalCharges,
          avgPrice: '',
          currentPrice: '',
          amount: totalAmounts,
          unrealizeProfit: totalProfitLoss,
          percentage: (avgPrecentage / this.availableStock.length)
        })




      this.options = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        calculable: false,
        series: {
          name: 'Company',
          type: 'pie',
          radius: [10, 130],
          roseType: 'Company',
          data: availableStockList,
          colorScheme: this.colorScheme
        }
      }
      console.log('equity holding pie chart');
      console.log(this.options);
      this.source = new LocalDataSource(this.availableStock);
    });

  }



  get f() { return this.buyForm.controls; }
  onSubmit() {

    this.submittedBuy = true;
    if (this.buyForm.invalid) {
      console.log(this.f);
      return;
    }
    let formData = this.buyForm.value;
    formData['userId'] = localStorage.getItem("userId");
    this.buyService.saveBuy(formData).subscribe((response: any[]) => {
      if (response['status'] == "fail") {
        Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
      } else {
        Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
        this.clearForm();
        this.loadDataTable();

        this.getTotalValuesToCards();
        this.loadPreviousDepositTotals();
        this.submittedBuy = false;
      }

    }, (err) => {
      Swal.fire('CSE Profile', 'Data Saving Problem', 'error');

    })
  }

  get s() { return this.sellForm.controls; }
  onSubmitSale() {
    this.submittedSell = true;
    if (this.sellForm.invalid) {
      return;
    }
    console.log('data submitting');
    let formData = this.sellForm.value;

    this.sellService.saveSell(formData).subscribe((response: any[]) => {
      if (response['status'] == "fail") {
        Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
      } else {
        Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
        this.clearSalesForm();
        this.loadDataTable();

        this.getTotalValuesToCards();
        this.loadPreviousDepositTotals();
        this.availableLots = [];
        this.submittedSell = false;
      }

    }, (err) => {
      Swal.fire('CSE Profile', 'Data Saving Problem', 'error');

    })
  }




  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.companyData.filter(optionValue => optionValue.label.toLowerCase().includes(filterValue));
  }

  onModelChange(value: any) {
    console.log(value);

    this.filteredNgModelOptions$ = of(this.filter(value.id));
  }


  dataSource: NbTreeGridDataSource<FSEntry>;
  min: Date;
  max: Date;

  sortColumn: string;
  sortDirection: NbSortDirection = NbSortDirection.NONE;
  amount: number;
  commissionAmount: number;
  netAmount: number;
  selCompany: string;
  maxQtySales: number;
  sellFormNetAmount: number;
  sellamountCharges: string;
  calculateAmount() {
    let formVal = this.buyForm.value;
    let price = formVal.price;
    let qty = formVal.qty;

    this.amount = Number(price) * Number(qty);
    this.commissionAmount = (this.amount / 100) * Number(this.tradeCommision);
    this.netAmount = Number(this.amount + this.commissionAmount);


    this.buyForm = this.formBuilder.group({
      company: [formVal.company],
      transDate: [formVal.transDate],
      price: [formVal.price],
      qty: [formVal.qty],
      amount: [this.amount.toFixed(2)],
      commission: [this.commissionAmount.toFixed(2)],
      netAmount: [this.netAmount.toFixed(2)]
    });


  }

  selAmountAll: string;
  netSellAmountAll: string;
  profitAmount: string;
  profitPresentage: string;
  profitStatus: Boolean;
  currentPrice: Number;
  calculateSellAmount() {
    let formVal = this.sellForm.value;
    let price = formVal.price;
    let qty = formVal.qty;
    this.profitAmount = "0.00";

    let amount = Number(price) * Number(qty);
    let commissionAmount = (amount / 100) * Number(this.tradeCommision);
    let ProfitForOneQty = Number(this.sellFormNetAmount / this.maxQtySales);
    let costAmount = (ProfitForOneQty * Number(qty)) + commissionAmount;

    this.selAmountAll = Number(amount).toFixed(2);
    this.netSellAmountAll = (Number(amount) + Number(commissionAmount)).toFixed(2);

    this.sellamountCharges = (commissionAmount).toFixed(2);

    let profit = Number(amount) - Number(costAmount);
    let profPres = profit / (this.sellFormNetAmount / 100);

    this.profitPresentage = profPres.toFixed(2);
    this.profitStatus = false;
    if (profit > 0) {
      this.profitStatus = true;
    }
    this.profitAmount = profit.toFixed(2);

    this.sellForm = this.formBuilder.group({
      company: [formVal.company, Validators.required],
      transDate: [formVal.date, Validators.required],
      price: [formVal.price, Validators.min(0.1)],
      qty: [formVal.qty, Validators.min(1)],
      userId: localStorage.getItem("userId"),
      amount: Number(amount).toFixed(2),
      charges: commissionAmount.toFixed(2),
      profit: profit.toFixed(2),
    });

  }
  clearForm() {
    this.buyForm = this.formBuilder.group({
      company: '',
      transDate: '',
      price: '',
      qty: '',
      amount: '',
      commission: '',
      netAmount: ''
    });

  }

  clearSalesForm() {
    this.sellForm = this.formBuilder.group({
      company: '',
      transDate: '',
      price: '',
      qty: '',
      userId: localStorage.getItem("userId"),
      amount: '',
      charges: '',
      profit: '',
    });

  }

  getTotalValuesToCards() {
    this.dashboardService.getTotalEarnings().subscribe((earnings: any) => {
      this.totalEquityHoldings = Number(earnings["totalEquityHoldings"]).toFixed(2);
      this.totalEarnings = Number(earnings["totalEarnings"]).toFixed(2);
      this.currentMonthEarnings = Number(earnings["currentMonthEarnings"]).toFixed(2);
      this.todayEarnings = Number(earnings["todayEarnings"]).toFixed(2);
    })
  }

  loadPreviousDepositTotals() {
    this.dashboardService.getAccountSummary().subscribe((data: any) => {
      this.totalDeposit = Number(data['totalInvesment']).toFixed(2);
      this.totalDepositWords = data['totalInvesmentInWord']
    })
  }

  loadCompanyStock(value: any) {
    this.availableLots = [];
    let formVal = this.sellForm.value;
    if (value == undefined) {
      value = formVal.company;
    }

    this.buyService.getAvailableStockDetailsByCompany(value).subscribe((availableLots: any[]) => {
      this.availableLots = availableLots;
      let sum = availableLots.reduce(function (a, b) {
        return {
          qty: a.qty + b.qty,
          amount: a.amount + b.amount
        };

      });

      this.maxQtySales = sum.qty;
      this.sellFormNetAmount = sum.amount;

      if (this.availableLots.length > 0) {
        this.currentPrice = this.availableLots[0]['currentPrice'];
        console.log('Current Price', this.currentPrice);
      }


      console.log('maxQtySales', this.maxQtySales);
      console.log('sellFormNetAmount', this.sellFormNetAmount);



      if (formVal.date == undefined) {
        formVal.date = new Date();
      }
      this.sellForm = this.formBuilder.group({
        company: [value, Validators.required],
        transDate: [formVal.date, Validators.required],
        price: [this.currentPrice, Validators.required],
        qty: [sum.qty, Validators.required],
        userId: localStorage.getItem("userId"),
        amount: formVal.amount,
        charges: formVal.charges,
        profit: formVal.profit,
      });
      this.sellSource = new LocalDataSource(this.availableLots);
      this.calculateSellAmount();
    });
  }

  updateSort(sortRequest: NbSortRequest): void {
    this.sortColumn = sortRequest.column;
    this.sortDirection = sortRequest.direction;
  }

  getSortDirection(column: string): NbSortDirection {
    if (this.sortColumn === column) {
      return this.sortDirection;
    }
    return NbSortDirection.NONE;
  }


  getShowOn(index: number) {
    const minWithForMultipleColumns = 400;
    const nextColumnStep = 100;
    if (index == 1) {
      return 600;
    } else {
      return minWithForMultipleColumns + (nextColumnStep * index);
    }


  }

  openWindowBuy(contentTemplateBuy) {
    this.windowService.open(
      contentTemplateBuy,
      {
        title: 'Buy Equity',
        context: {
          text: 'some text to pass into template',
        },
      },
    );
  }
  openWindow(contentTemplate, event) {

    this.selCompany = event.data.id;
    this.sellForm = this.formBuilder.group({
      company: [event.data.id, Validators.required],
      transDate: [new Date(), Validators.required],
      price: ['', Validators.required],
      qty: ['', Validators.required],
      userId: localStorage.getItem("userId"),
      amount: '0.00',
      charges: '0.00',
      profit: '0.00',
    });

    this.selAmountAll = '';
    this.netSellAmountAll = '';
    this.profitAmount = '';
    this.sellamountCharges = '';

    this.loadCompanyStock(event.data.id);

    this.windowService.open(
      contentTemplate,
      {
        title: 'Sell Equity',
        context: {
          text: 'some text to pass into template',
        },
      },
    );
  }

}

@Component({
  selector: 'ngx-fs-icon',
  template: `
    <nb-tree-grid-row-toggle [expanded]="expanded" *ngIf="isDir(); else fileIcon">
    </nb-tree-grid-row-toggle>

    <ng-template #fileIcon>
      <nb-icon icon="file-text-outline"></nb-icon>
    </ng-template>
  `,
})
export class FsIconComponent {
  @Input() kind: string;
  @Input() expanded: boolean;
  @Input() hasChild: boolean;

  isDir(): boolean {
    return this.kind === 'dir';
  }

  isChild(): boolean {
    if (this.kind === 'dir') {
      return this.hasChild = true;
    } else {
      return this.hasChild = false;
    }

  }
}
