import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import Swal from 'sweetalert2'
import { map, startWith } from 'rxjs/operators';
import { CompanyService } from 'app/services/company.service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { LocalDataSource } from 'ng2-smart-table';
import { SplitService } from 'app/services/split.service';

@Component({
  selector: 'ngx-split',
  templateUrl: './split.component.html',
  styleUrls: ['./split.component.scss']
})
export class SplitComponent implements OnInit {
  currentQtyVal = 0;
  futureQtyAmount = 0;
  constructor(private companyService: CompanyService, private splitService: SplitService, private formBuilder: FormBuilder) { }
  submitted = false;
  companyData: any;
  splitForm: FormGroup;
  ngOnInit() {
    this.loadCompanies();
    this.splitForm = this.formBuilder.group({
      company: ['', Validators.required],
      userId: localStorage.getItem('userId'),
      splitDate: ['', Validators.required],
      currentQty: [0, Validators.min(1)],
      futureQty: [0, Validators.min(1)],
      outQty: [0, Validators.min(1)],
      fromQty: [0, Validators.min(1)],
      currentPrice: [0, Validators.min(1)],
      newPrice: [0, Validators.min(1)]
    });
    this.loadSplitedDataToTable();
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
      edit: false
    },
    columns: {
      company: {
        title: 'Company',
        type: 'string'
      },

      splitDate: {
        title: 'Split Date',
        type: 'string',
        valuePrepareFunction: function (value) { return value.split('T')[0] },
      },
      outQty: {
        title: 'Out Qty',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
      },
      fromQty: {
        title: 'From Qty',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
      },
      currentQty: {
        title: 'Previous Qty',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
      },

      currentPrice: {
        title: 'Previous Price',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
      },

      newPrice: {
        title: 'New Price',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + Number(value).toFixed(2) + ' </div>' },
        filter: false,
      },

      futureQty: {
        title: 'Generated Qty',
        type: 'html',
        valuePrepareFunction: function (value) { return '<div align="right"> ' + value + ' </div>' },
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
      if (result.isConfirmed) {
        this.splitService.deleteSplit(event.data.id).subscribe((response: any[]) => {
          if (response["status"] == "success") {
            Swal.fire(
              'Deleted!',
              'Your Record has been deleted.',
              'success'
            );
            this.loadSplitedDataToTable();
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



  loadCompanies() {
    this.companyService.getCompanies().subscribe((company: any[]) => {
      this.companyData = company;
    });

  }
  get f() { return this.splitForm.controls; }
  onSubmit() {
    this.submitted = true;
    if (!this.splitForm.invalid) {
      this.splitService.saveSplit(this.splitForm.value).subscribe((response: any[]) => {
        if (response['status'] == "fail") {
          Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
        } else {
          this.loadSplitedDataToTable();
          Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
          this.clearForm();
          this.submitted = false;
        }

      })
    }

  }

  loadCurrentAvailability() {
    var frmVal = this.splitForm.value;
    var companyId = frmVal.company;
    var currentPrice = 0;
    console.log('selected company ', companyId);
    this.splitService.loadCurrentAvailability(localStorage.getItem("userId"), companyId).subscribe((response: any[]) => {
      let currentQty = response.reduce(function (prev, cur) {
        return prev + cur.qty;
      }, 0);

      this.splitService.loadCurrentAvgPrice(localStorage.getItem("userId")).subscribe((res: any[]) => {
        let data = res.filter(stock => stock.id == companyId);
        if (data.length > 0) {
          currentPrice = data[0].avgPrice.toFixed(2);
        }
        this.splitForm = this.formBuilder.group({
          company: [frmVal.company, Validators.required],
          userId: localStorage.getItem('userId'),
          splitDate: [frmVal.splitDate, Validators.required],
          currentQty: currentQty,
          futureQty: frmVal.futureQty,
          outQty: frmVal.outQty,
          fromQty: frmVal.fromQty,
          currentPrice: currentPrice,
          newPrice: frmVal.newPrice,
        });
      });

    })
  }

  calculateFutureAmount() {
    var frmVal = this.splitForm.value;
    let currentQty = frmVal.currentQty;
    let outputQty = frmVal.outQty;
    let fromQty = frmVal.fromQty;
    let presentage = Number(currentQty) / Number(fromQty);
    let futureQty = presentage * Number(outputQty);

    let currentPrice = frmVal.currentPrice;
    let spitRatio = Number(outputQty) / Number(fromQty);
    let newPrice = Number(currentPrice) / spitRatio;


    this.splitForm = this.formBuilder.group({
      company: [frmVal.company, Validators.required],
      userId: localStorage.getItem('userId'),
      splitDate: [frmVal.splitDate, Validators.required],
      currentQty: frmVal.currentQty,
      futureQty: parseInt(futureQty.toString()),
      outQty: frmVal.outQty,
      fromQty: frmVal.fromQty,
      currentPrice: frmVal.currentPrice,
      newPrice: newPrice.toFixed(2),
    });
  }
  clearForm() {
    this.splitForm = this.formBuilder.group({
      company: '',
      splitDate: '',
      currentQty: '',
      futureQty: '',
      outQty: 0,
      fromQty: 0,
      currentPrice: 0,
      newPrice: 0
    });
  }
  loadSplitedDataToTable() {
    this.splitService.loadSplitedDataToTable(localStorage.getItem("userId")).subscribe((response: any[]) => {
      this.source = new LocalDataSource(response);
    })
  }



}
