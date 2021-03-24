import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { CompanyService } from 'app/services/company.service';
import { DepositService } from 'app/services/deposit.service';
import Swal from 'sweetalert2'
import { LocalDataSource } from 'ng2-smart-table';
@Component({
  selector: 'ngx-diposit',
  templateUrl: './diposit.component.html',
  styleUrls: ['./diposit.component.scss']
})
export class DipositComponent implements OnInit {

  depositForm:FormGroup;
  submitted=false;
  totalDeposit="0.00";
  currentMonthDeposit="0.00";
  source: LocalDataSource = new LocalDataSource();
  constructor(protected dateService: NbDateService<Date>,
    private formBuilder:FormBuilder,
    private depositService : DepositService) { }

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
        deleteButtonContent: '<i class="nb-trash" title="Delete Deposit"></i>',
        confirmDelete: true,
      },
      actions: {
        add: false,
        edit:false,
        
        },
      columns: {
        date: {
          title: 'Date',
          type: 'string',
          filter: false,
          valuePrepareFunction: function (value) { return   value.split('T')[0]   },
        },
        amount: {
          title: 'Deposit Amount',
          type: 'html',
          filter: false,
          valuePrepareFunction: function (value) { return '<div align="right">   ' + Number(value).toFixed(2) + ' </div>' },
        },
       
      },
    };

  ngOnInit(): void {
    this.depositForm = this.formBuilder.group({
      userId:localStorage.getItem('userId'),
      date:'',
      amount: 0
    });
    this.loadDeposit();
    this.loadPreviousDepositTotals();
  }

  onSubmit(){
    this.submitted=true;
    this.depositService.saveDeposit(this.depositForm.value).subscribe((response:any)=>{
      if (response['status'] == "fail") {
        Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
      } else {
        
        Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
        this.loadDeposit();
        this.loadPreviousDepositTotals();
        this.clearForm();
        this.submitted=false;
      }
    });
  }
  clearForm() {
    this.depositForm = this.formBuilder.group({
      userId:localStorage.getItem('userId'),
      date:'',
      amount: 0
    });
  }
 

  loadDeposit(){
    this.depositService.loadDeposit(localStorage.getItem("userId")).subscribe((data:any[])=>{
      this.source = new LocalDataSource(data);
    })
  }

  loadPreviousDepositTotals(){
    this.depositService.getPreviousDeposits().subscribe((data:any)=>{
      this.totalDeposit= Number(data['totalDeposit']).toFixed(2);
      this.currentMonthDeposit =Number(data['currentMonthDeposit']).toFixed(2);
    })
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
        this.depositService.deleteDeposit(event.data.id).subscribe((response:any[])=>{
            if(response["status"]=="success"){
             
              Swal.fire(
                'Deleted!',
                'Your Record has been deleted.',
                'success'
              );
              this.loadDeposit();
              this.loadPreviousDepositTotals();
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

}
