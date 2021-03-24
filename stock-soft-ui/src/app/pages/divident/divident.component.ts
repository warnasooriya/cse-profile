import { Component,  ViewChild,OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import Swal from 'sweetalert2'
import { map, startWith } from 'rxjs/operators';
import { CompanyService } from 'app/services/company.service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { DividentService } from 'app/services/divident.service';
import { LocalDataSource } from 'ng2-smart-table';

@Component({
  selector: 'ngx-divident',
  templateUrl: './divident.component.html',
  styleUrls: ['./divident.component.scss']
})
export class DividentComponent implements OnInit {
  [x: string]: any;
 
  divForm:FormGroup;
  source: LocalDataSource = new LocalDataSource();
  
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


  constructor(private companyService:CompanyService,  protected dateService: NbDateService<Date>,
    private formBuilder:FormBuilder,
    private dividentService : DividentService
    ) { }

  submitted=false;
  companyData:any;
  filteredControlOptions$: Observable<string[]>;
  filteredNgModelOptions$: Observable<string[]>;
  inputFormControl: FormControl;
  value: string;
  isDisabledCash=false;
  isDisabledQty=false;
  ngOnInit() {
    this.loadCompanies();
    this.divForm = this.formBuilder.group({
      company: ['', Validators.required],
      userId:localStorage.getItem('userId'),
      divxdDate: ['', Validators.required],
      divDate: ['', Validators.required],
      currentPrice:0,
      equityAvailable: false,
      cashAvailable: false,
      equity: 0,
      cashAmount: 0
    });
    this.dissableChecker();
    this.loadDividendToTable();
      
  }

  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.companyData.filter(optionValue => optionValue.label.toLowerCase().includes(filterValue));
  }


  onModelChange(value: string) {
    this.filteredNgModelOptions$ = of(this.filter(value));
  }


  loadCompanies(){
    this.companyService.getCompanies().subscribe((company: any[]) => {
      this.companyData = company;
    });

  }

  onSubmit(){
    this.submitted=true;
    this.dividentService.saveDivident(this.divForm.value).subscribe((response:any[])=>{
      if (response['status'] == "fail") {
          Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
        } else {
          this.loadDividendToTable();
          Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
          this.clearForm();
          this.submitted=false;
        }

    })
  }

  clearForm() {
    this.divForm = this.formBuilder.group({
      company: '',
      divxdDate: '',
      divDate:'',
      equityAvailable: false,
      cashAvailable: false,
      currentPrice:0,
      equity: 0,
      cashAmount: 0
    });
    this.dissableChecker();
  }

  loadDividendToTable(){
    this.dividentService.loadDividendToTable(localStorage.getItem("userId")).subscribe((response:any[])=>{
      this.source = new LocalDataSource(response);
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
        this.dividentService.deleteDividend(event.data.id).subscribe((response:any[])=>{
            if(response["status"]=="success"){
             
              Swal.fire(
                'Deleted!',
                'Your Record has been deleted.',
                'success'
              );
              this.loadDividendToTable();
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
  dissableChecker(){

    let formdata =this.divForm.value;
    if(formdata.cashAvailable==true){
      this.isDisabledCash=false;
    }else{
      this.isDisabledCash=true;
      this.divForm.patchValue({
        'cashAmount': '0'
    });
    }

    if(formdata.equityAvailable==true){
      this.isDisabledQty=false;
    }else{
      this.isDisabledQty=true;
      this.divForm.patchValue({
        'equity': '0'
    });
    }

  }
}
