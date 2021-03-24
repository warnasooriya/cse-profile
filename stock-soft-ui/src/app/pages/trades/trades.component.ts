import { ChangeDetectionStrategy, Component, Input, ViewChild, OnInit } from '@angular/core';

import { NbSortDirection, NbSortRequest, NbTreeGridDataSource, NbTreeGridDataSourceBuilder, NbWindowService, NbDateService } from '@nebular/theme';
import { Observable, of } from 'rxjs';
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
    backgroundColor: ['#ff5252','#6200EA','#CDDC39','#795548','#673AB7','#EC407A', '#9CCC65', '#388E3C', '#FFCA28','#BCAAA4']
  };
  submittedBuy =false;
  submittedSell =false;
  totalEquityHoldings="0.00";
  totalEarnings="0.00";
  currentMonthEarnings="0.00"
  todayEarnings="0.00";
  totalDeposits="0.00";

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
      add: false,
      edit: false,
      delete: true,
    },
    columns: {
      company: {
        title: 'Company',
        type: 'string',
      },
      date: {
        title: 'Date',
        type: 'string',
      },
      qty: {
        title: 'Qty',
        type: 'number',
        filter: false
      },
      price: {
        title: ' Price',
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

      amount: {
        title: 'Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
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


  constructor(
    private windowService: NbWindowService,
    protected dateService: NbDateService<Date>,
    private companyService: CompanyService,
    private buyService: BuyService,
    private formBuilder: FormBuilder,
    private sellService: SellService,
    private dashboardService:DashboardService,
    private depositService:DepositService,
    private layoutService: LayoutService
  ) {
    this.min = this.dateService.addDay(this.dateService.today(), -5);
    this.max = this.dateService.addDay(this.dateService.today(), 5);
    this.layoutService.onSafeChangeLayoutSize()
    .pipe(
      takeWhile(() => this.alive),
    )
    .subscribe(() => this.resizeChart());
    
    this.loadDataTable();

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
      company: ['',Validators.required],
      transDate: ['',Validators.required],
      price: ['',Validators.required],
      userId: ['',Validators.required],
      qty: ['',Validators.required],
      amount: ['',Validators.required],
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
    var availableStockList=[];
    //var asl=[{"id":"2c9fa2f9764202850176420e86210002","company":"DFCC BANK PLC","code":"DFCC.N0000","date":"2020-08-20","qty":1600.00,"price":62.90000000,"avgPrice":63.60448000000000,"chargers":1127.1680000000,"amount":101767.1680000000,"lotNumbers":"af3042d2-0bfb-42a5-a5c3-f61a17995c84"},{"id":"2c9fa2f9764202850176420ee4020003","company":"INDUSTRIAL ASPHALTS (CEYLON) PLC","code":"ASPH.N0000","date":"2020-09-30","qty":300001.00,"price":0.30000000,"avgPrice":0.30336000000000,"chargers":1008.0033600000,"amount":91008.3033600000,"lotNumbers":"db8535ab-9201-40a4-b930-cfda0a25d249,be56b8aa-51c5-4274-83c4-269cd9e3ecf6,edb4eb64-e2f4-4609-a0a4-5b2227ed5627"},{"id":"2c9fa2f9764202850176420f2f660004","company":"BROWNS INVESTMENTS PLC","code":"BIL.N0000","date":"2021-01-07","qty":25000.00,"price":6.30000000,"avgPrice":6.37056000000000,"chargers":1764.0000000000,"amount":159264.0000000000,"lotNumbers":"f31f690a-41a1-4782-9af4-5fa5c099397c"},{"id":"2c9fa2f97642028501764211693d000a","company":"NATIONAL DEVELOPMENT BANK PLC","code":"NDB.N0000","date":"2020-12-24","qty":3300.00,"price":77.67272727,"avgPrice":78.54266181818182,"chargers":2870.7840000000,"amount":259190.7840000000,"lotNumbers":"d4f79277-0789-469f-9bce-6978aea683ee,9b0a351b-4d8a-4653-aaa6-d89864583aa2"},{"id":"2c9fa2f976ce12820176d141ecc30005","company":"LANKA HOSPITALS CORPARATION PLC","code":"LHCL.N0000","date":"2021-01-06","qty":6402.00,"price":58.60246798,"avgPrice":59.25881562011871,"chargers":4201.9376000000,"amount":379374.9376000000,"lotNumbers":"f4953813-ef14-4108-a557-28806f1fbf12,cd5596d8-ebed-4af2-afe8-dda6b9964dc3,90d7cadd-69b2-4c8d-9281-6825f036a33b"}];
    this.buyService.getAvailableStock().subscribe((asl: any[]) => {
      this.availableStock = asl;
    
      asl.forEach(function(company) {
        var amount = company['amount'];
        var availableStockObj = {"name":company['code'] , "value":amount };
        availableStockList.push(availableStockObj);
      });
      
      this.options={
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      calculable: false,
      series:{
        name: 'Company',
        type: 'pie',
        radius: [10, 130],
        roseType: 'Company',
        data:availableStockList,
        colorScheme:this.colorScheme
      }
    }
      console.log('equity holding pie chart');
      console.log(this.options);

      this.source = new LocalDataSource(this.availableStock);
      
     
    });

    //  var sl = [
    //    {name: "DFCC.N0000", value: 101767.168},
    //    {name: "ASPH.N0000", value: 91008.30336},
    //    {name: "BIL.N0000", value: 159264},
    //    {name: "NDB.N0000", value: 259190.784},
    //    {name: "LHCL.N0000", value: 379374.9376}
    // ];
     
    
    // var series = {
    //   name: 'Company',
    //   type: 'pie',
    //   radius: [10, 130],
    //   roseType: 'Company',
    //   data:sl,
    //   colorScheme:this.colorScheme
    // }
    // this.options.series.push(series);
    // console.log(this.options);
  }

  


  onSubmit() {

    this.submittedBuy = true;
    if(this.buyForm.invalid){
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


  onSubmitSale() {
    this.submittedSell = true;
    if(this.sellForm.invalid){
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
  calculateSellAmount() {
    let formVal = this.sellForm.value;
    let price = formVal.price;
    let qty = formVal.qty;

    let amount = Number(price) * Number(qty);
    let commissionAmount = (amount / 100) * Number(this.tradeCommision);
    let ProfitForOneQty = Number(this.sellFormNetAmount / this.maxQtySales);
    let costAmount = (ProfitForOneQty * Number(qty)) + commissionAmount;

    this.selAmountAll = Number(amount).toFixed(2);
    this.netSellAmountAll = (Number(amount) + Number(commissionAmount)).toFixed(2);

    this.sellamountCharges = (commissionAmount).toFixed(2);

    let profit = Number(amount) - Number(costAmount);
    this.profitAmount = profit.toFixed(2);
    this.sellForm = this.formBuilder.group({
      company: [formVal.company, Validators.required],
      transDate: [formVal.date, Validators.required],
      price: [formVal.price, Validators.required],
      qty: [formVal.qty, Validators.required],
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

  getTotalValuesToCards(){
    this.dashboardService.getTotalEarnings().subscribe((earnings:any)=>{
      this.totalEquityHoldings= Number(earnings["totalEquityHoldings"]).toFixed(2);
      this.totalEarnings= Number(earnings["totalEarnings"]).toFixed(2);
      this.currentMonthEarnings= Number(earnings["currentMonthEarnings"]).toFixed(2);
      this.todayEarnings= Number(earnings["todayEarnings"]).toFixed(2);
    })
  }

  loadPreviousDepositTotals(){
    this.depositService.getPreviousDeposits().subscribe((data:any)=>{
      this.totalDeposit= Number(data['totalDeposit']).toFixed(2);
    })
  }

  loadCompanyStock(value: any) {
    this.availableLots=[];
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

      console.log('maxQtySales', this.maxQtySales);
      console.log('sellFormNetAmount', this.sellFormNetAmount);
      if (formVal.date == undefined) {
        formVal.date = new Date();
      }
      this.sellForm = this.formBuilder.group({
        company: [value, Validators.required],
        transDate: [formVal.date, Validators.required],
        price: [formVal.price, Validators.required],
        qty: [sum.qty, Validators.required],
        userId: localStorage.getItem("userId"),
        amount: formVal.amount,
        charges: formVal.charges,
        profit: formVal.profit,
      });
      this.sellSource = new LocalDataSource(this.availableLots);
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
