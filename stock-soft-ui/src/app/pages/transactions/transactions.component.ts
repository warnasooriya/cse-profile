import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { FormGroup, FormBuilder } from '@angular/forms';
import { TransactionsService } from 'app/services/transactions.service';

@Component({
  selector: 'ngx-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit {
  data:any;
  fromDate:Date;
  source: LocalDataSource = new LocalDataSource();
  transForm:FormGroup;
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
      edit:false,
      delete:false
      
      },
    columns: {
      transDate: {
        title: 'Date',
        type: 'string',
        filter: false,
      },
      transType: {
        title: 'Transaction Type',
        type: 'string',
        filter: false,
      },
      inAmount: {
        title: 'In Amount',
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      outAmount: {
        title: 'Out Amount',
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      balance: {
        title: 'Cash In Hand',
       
        filter: false,
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
      },
     
    },
  };
  constructor(     private formBuilder: FormBuilder , private transService:TransactionsService) { }

  ngOnInit(): void {
    var date = new Date();
    var firstDay =  new Date(date.getFullYear(), date.getMonth(), 1);
    this.transForm = this.formBuilder.group({
      fromDate: [firstDay],     
    });
    this.searchTrans();
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

  searchTrans(){
    let userId=localStorage.getItem("userId");
    let formData = this.transForm.value;
    var from = this.formatDate(formData.fromDate);
    this.data={"fromDate":from,"userId":userId};
    this.transService.getTransactions(this.data).subscribe((buydata:any[])=>{
      this.source.load(buydata); 
    });
  }

}
