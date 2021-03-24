import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { SmartTableData } from 'app/@core/data/smart-table';
import { SellService } from 'app/services/sell.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import Swal from 'sweetalert2'

@Component({
  selector: 'ngx-sell',
  templateUrl: './sell.component.html',
  styleUrls: ['./sell.component.scss']
})
export class SellComponent implements OnInit {

  data:any;
  sellFilterForm:FormGroup;
  totalSumData:any;
  
  settings = {
    
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
      deleteButtonContent: '<i class="nb-trash"></i>',
      confirmDelete: true,
    },
    actions: {
      add: false,
      edit:false
      },
    columns: {
      transDate: {
        title: 'Date',
        type: 'string',
        valuePrepareFunction: function (value) { return   value.split('T')[0]   },
      },
      company: {
        title: 'Company',
        type: 'string',
      },
      qty: {
        title: 'Qty',
        type: 'number',
      },
      price: {
        title: 'Price',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      commission: {
        title: 'Charges',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        
      },
      netAmount: {
        title: 'Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
    },
  };
  source: LocalDataSource = new LocalDataSource();

  constructor(
    private service: SmartTableData ,
     private sellService:SellService,
     private formBuilder: FormBuilder
     
     ) {
    // const data = this.service.getData();
    // this.source.load(data);
  }

  
  onDeleteConfirm(event): void {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.sellService.deleteSales(event.data.id).subscribe((response:any[])=>{
            if(response["status"]=="success"){
             
              Swal.fire(
                'Deleted!',
                'Your Record has been deleted.',
                'success'
              );
              this.loadSellData();
            }else{
              Swal.fire(
                'Problem!',
                'Your Record Deleting Problem',
                'error'
              )
            }
        })
        
      }
    })
  }
 
  ngOnInit() {
    var date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    this.sellFilterForm = this.formBuilder.group({
      fromDate: [firstDay],
      toDate: [lastDay]
     
    });
    this.loadSellData();

  }

  
  loadSellData(){
    let userId=localStorage.getItem("userId");
    let formData = this.sellFilterForm.value;
    var from = this.formatDate(formData.fromDate);
    var to = this.formatDate(formData.toDate);

    this.data={"fromDate":from, "toDate":to ,"userId":userId};
    this.sellService.getSellData(this.data).subscribe((buydata:any[])=>{
      this.source.load(buydata['purchaseList']);
      this.totalSumData = Number(buydata['sumTotal']).toFixed(2);
 
    });
  }
 
  searchSell(){
    this.loadSellData();
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
