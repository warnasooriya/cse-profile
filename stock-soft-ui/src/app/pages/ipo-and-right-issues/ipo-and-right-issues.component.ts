import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import Swal from 'sweetalert2'
import { map, startWith } from 'rxjs/operators';
import { CompanyService } from 'app/services/company.service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { DividentService } from 'app/services/divident.service';
import { LocalDataSource } from 'ng2-smart-table';
import { IporiService } from 'app/services/ipori.service';
@Component({
  selector: 'ngx-ipo-and-right-issues',
  templateUrl: './ipo-and-right-issues.component.html',
  styleUrls: ['./ipo-and-right-issues.component.scss']
})
export class IpoAndRightIssuesComponent implements OnInit {
  [x: string]: any;

  ipoRIForm: FormGroup;
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
      edit: false
    },
    columns: {
      transType: {
        title: 'Transaction',
        type: 'string'
      },
      company: {
        title: 'Company',
        type: 'string'
      },
      date: {
        title: 'Date',
        type: 'string',
        valuePrepareFunction: function (value) { return value.split('T')[0] },
      },

      qty: {
        title: 'Qty',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
      },
      price: {
        title: 'Price',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false,
      },
      amount: {
        title: 'Amount',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false,
      },

    },
  };
  constructor(private companyService: CompanyService, protected dateService: NbDateService<Date>,
    private formBuilder: FormBuilder,
    private ipoRIService: IporiService
  ) { }


  submitted = false;
  companyData: any;
  transactionType = [{ "id": "IPO", "type": "IPO" }, { "id": "RIGHT-ISSUES", "type": "Right Issue" }];
  filteredControlOptions$: Observable<string[]>;
  filteredNgModelOptions$: Observable<string[]>;
  inputFormControl: FormControl;
  value: string;
  isDisabledCash = false;
  isDisabledQty = false;

  ngOnInit() {
    this.loadCompanies();
    this.ipoRIForm = this.formBuilder.group({
      transType: ['', Validators.required],
      company: ['', Validators.required],
      userId: localStorage.getItem('userId'),
      date: ['', Validators.required],
      qty: [0, Validators.min(1)],
      price: [0, Validators.required],
      amount: 0
    });

    this.loadDIOPAndIRToTable();

  }


  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.companyData.filter(optionValue => optionValue.label.toLowerCase().includes(filterValue));
  }


  onModelChange(value: string) {
    this.filteredNgModelOptions$ = of(this.filter(value));
  }


  loadCompanies() {
    this.companyService.getCompanies().subscribe((company: any[]) => {
      this.companyData = company;
    });

  }
  get f() { return this.ipoRIForm.controls; }
  onSubmit() {
    this.submitted = true;
    if (!this.ipoRIForm.invalid) {
      this.ipoRIService.saveIpoRI(this.ipoRIForm.value).subscribe((response: any[]) => {
        if (response['status'] == "fail") {
          Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
        } else {
          this.loadDIOPAndIRToTable();
          Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
          this.clearForm();
          this.submitted = false;
        }

      })
    }
  }

  calculateAmount() {
    let formValue = this.ipoRIForm.value;
    let qty = Number(formValue.qty);
    let price = Number(formValue.price);
    let amount = (qty * price).toFixed(2);
    this.ipoRIForm = this.formBuilder.group({
      transType: [formValue.transType, Validators.required],
      company: [formValue.company, Validators.required],
      userId: localStorage.getItem('userId'),
      date: [formValue.date, Validators.required],
      qty: [formValue.qty, Validators.required],
      price: [formValue.price, Validators.required],
      amount: amount
    });


  }


  clearForm() {
    this.ipoRIForm = this.formBuilder.group({
      transType: '',
      company: '',
      date: '',
      qty: '',
      price: '',
      amount: ''
    });

  }

  loadDIOPAndIRToTable() {
    this.ipoRIService.loadDIOPAndIRToTable(localStorage.getItem("userId")).subscribe((response: any[]) => {
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
        this.ipoRIService.deleteIpoRI(event.data.id, this.ipoRIForm.value.transType).subscribe((response: any[]) => {
          if (response["status"] == "success") {
            Swal.fire(
              'Deleted!',
              'Your Record has been deleted.',
              'success'
            );
            this.loadDIOPAndIRToTable();
          } else {
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
