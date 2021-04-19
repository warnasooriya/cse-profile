import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import Swal from 'sweetalert2'
import { map, startWith } from 'rxjs/operators';
import { CompanyService } from 'app/services/company.service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { LocalDataSource } from 'ng2-smart-table';

@Component({
  selector: 'ngx-split',
  templateUrl: './split.component.html',
  styleUrls: ['./split.component.scss']
})
export class SplitComponent implements OnInit {

  constructor(private companyService:CompanyService,) { }
  submitted=false;
  companyData:any;
  splitForm:FormGroup;
  ngOnInit(): void {
  }

  
  settings = {
    
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-edit" title="edit"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash" title="delete"></i>',
      confirmDelete: true,
    },
    actions: {
      add: false,
      edit:false
      },
    columns: {
      company: {
        title: 'Company',
        type: 'string'
      },
      divDate: {
        title: 'Divident Date',
        type: 'string',
        valuePrepareFunction: function (value) { return   value.split('T')[0]   },
      },
      xdate: {
        title: 'X Date',
        type: 'string',
        valuePrepareFunction: function (value) { return   value.split('T')[0]   },
      },
      price: {
        title: 'Current Price',
        type: 'html',
        filter:false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },
      equity: {
        title: 'Equity Qty',
        type: 'number',
        filter: false,
      },
      amount: {
        title: 'Cash Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false,
      },
     
    },
  };

  source: LocalDataSource = new LocalDataSource();
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
      // if (result.isConfirmed) {
      //   this.dividentService.deleteDividend(event.data.id).subscribe((response:any[])=>{
      //       if(response["status"]=="success"){
             
      //         Swal.fire(
      //           'Deleted!',
      //           'Your Record has been deleted.',
      //           'success'
      //         );
      //         this.loadDividendToTable();
      //       }else{
      //         Swal.fire(
      //           'Problem!',
      //           'Your Record Deleting Problem',
      //           'error'
      //         )
      //       }
      //   })
        
      // }
    })
  }


  
  loadCompanies(){
    this.companyService.getCompanies().subscribe((company: any[]) => {
      this.companyData = company;
    });

  }

  onSubmit(){
    this.submitted=true;
    // this.dividentService.saveDivident(this.divForm.value).subscribe((response:any[])=>{
    //   if (response['status'] == "fail") {
    //       Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
    //     } else {
    //       this.loadDividendToTable();
    //       Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
    //       this.clearForm();
    //       this.submitted=false;
    //     }

    // })
  }

}
