import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DashboardService } from 'app/services/dashboard.service';
import { LocalDataSource } from 'ng2-smart-table';

@Component({
  selector: 'ngx-profit',
  templateUrl: './profit.component.html',
  styleUrls: ['./profit.component.scss']
})
export class ProfitComponent implements OnInit {
  totalProfit: Number;
  data: any;
  fromDate: Date;
  source: LocalDataSource = new LocalDataSource();
  profitForm: FormGroup;
  settings = {
    pager: {
      display: true,
      perPage: 15
    },
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-edit"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash" title="Delete Deposit"></i>',
      confirmDelete: false,
    },
    actions: {
      add: false,
      edit: false,
      delete: false

    },
    columns: {
      salesDate: {
        title: 'Date',
        type: 'string',
        filter: false,
      },
      code: {
        title: 'Company',
        type: 'string',
        filter: false,
      },
      purchaseAmount: {
        title: 'Invest Amount',
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      salesAmount: {
        title: 'Realized Amount',
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      profit: {
        title: 'Profit',
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },

    },
  };

  constructor(private formBuilder: FormBuilder, private dashboardService: DashboardService) { }

  ngOnInit(): void {
    var date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    this.profitForm = this.formBuilder.group({
      fromDate: [firstDay],
      toDate: [lastDay]
    });
    this.searchProfit();
  }

  searchProfit() {
    let userId = localStorage.getItem("userId");
    let formData = this.profitForm.value;
    var from = this.formatDate(formData.fromDate);
    var to = this.formatDate(formData.toDate);
    this.data = { "fromDate": from, "toDate": to, "userId": userId };

    this.dashboardService.getProfitDetails(this.data).subscribe((profitData: any[]) => {
      this.totalProfit = profitData.map(item => item.profit).reduce((prev, next) => prev + next);
      this.source.load(profitData);
    });

    //   var dummy = [
    //     {
    //         "id": "ebe38499-4ffd-11eb-9406-74d83e4ed2c9",
    //         "salesDate": "2021-01-05 12:00",
    //         "code": "EXPO.N0000",
    //         "name": "EXPOLANKA HOLDINGS PLC",
    //         "purchaseAmount": 209925.1200000,
    //         "salesAmount": 232565.76,
    //         "profit": 10000
    //     },
    //     {
    //         "id": "ebe36bce-4ffd-11eb-9406-74d83e4ed2c9",
    //         "salesDate": "2020-12-22 12:00",
    //         "code": "SFIN.0000",
    //         "name": "Singer Finance (Lanka) PLC",
    //         "purchaseAmount": 212792.8872000,
    //         "salesAmount": 229896.00,
    //         "profit": 5000
    //     },

    // ];

    // this.totalProfit = dummy.map(item => item.profit).reduce((prev, next) => prev + next);
    // this.source.load(dummy); 
  }



  formatDate(date) {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [year, month, day].join('-');
  }

}
