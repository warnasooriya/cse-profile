import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDateService } from '@nebular/theme';
import { CompanyService } from 'app/services/company.service';
import { DepositService } from 'app/services/deposit.service';
import Swal from 'sweetalert2'
import { LocalDataSource } from 'ng2-smart-table';
import { WidthdrawService } from 'app/services/widthdraw.service';

@Component({
  selector: 'ngx-cash-withdraw',
  templateUrl: './cash-withdraw.component.html',
  styleUrls: ['./cash-withdraw.component.scss']
})
export class CashWithdrawComponent implements OnInit {

  widthdrawForm: FormGroup;
  submitted = false;
  totalWidthdraw = "0.00";
  currentMonthWidthdraw = "0.00";
  source: LocalDataSource = new LocalDataSource();
  constructor(protected dateService: NbDateService<Date>,
    private formBuilder: FormBuilder,
    private widthdrawService: WidthdrawService) { }

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
      edit: false,

    },
    columns: {
      date: {
        title: 'Date',
        type: 'string',
        filter: false,
        valuePrepareFunction: function (value) { return value.split('T')[0] },
      },
      amount: {
        title: 'Widthdraw Amount',
        type: 'html',
        filter: false,
        valuePrepareFunction: function (value) { return '<div align="right">   ' + Number(value).toFixed(2) + ' </div>' },
      },

    },
  };

  ngOnInit(): void {
    this.widthdrawForm = this.formBuilder.group({
      userId: localStorage.getItem('userId'),
      date: ['', Validators.required],
      amount: [0, Validators.min(1)]
    });
    this.loadWidthdrw();
    this.loadPreviousWidthdrws();
  }
  get f() { return this.widthdrawForm.controls; }
  onSubmit() {
    this.submitted = true;
    if (!this.widthdrawForm.invalid) {
      this.widthdrawService.saveWidthdrw(this.widthdrawForm.value).subscribe((response: any) => {
        if (response['status'] == "fail") {
          Swal.fire('CSE Profile', 'Data Saving Problem', 'error');
        } else {

          Swal.fire('CSE Profile', 'Data Saved Successfully!', 'success');
          this.loadWidthdrw();
          this.loadPreviousWidthdrws();
          this.clearForm();
          this.submitted = false;
        }
      });
    }
  }
  clearForm() {
    this.widthdrawForm = this.formBuilder.group({
      userId: localStorage.getItem('userId'),
      date: '',
      amount: 0
    });
  }


  loadWidthdrw() {
    this.widthdrawService.loadWidthdrw(localStorage.getItem("userId")).subscribe((data: any[]) => {
      this.source = new LocalDataSource(data);
    })
  }

  loadPreviousWidthdrws() {
    this.widthdrawService.getPreviousWidthdrws().subscribe((data: any) => {
      this.totalWidthdraw = Number(data['totalWidthdrw']).toFixed(2);
      this.currentMonthWidthdraw = Number(data['currentMonthWidthdrw']).toFixed(2);
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
        this.widthdrawService.deleteWidthdrw(event.data.id).subscribe((response: any[]) => {
          if (response["status"] == "success") {

            Swal.fire(
              'Deleted!',
              'Your Record has been deleted.',
              'success'
            );
            this.loadWidthdrw();
            this.loadPreviousWidthdrws();
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
