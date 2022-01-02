import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { NbDateService } from '@nebular/theme';
import { BuyService } from 'app/services/buy.service';
import { CompanyService } from 'app/services/company.service';



@Component({
  selector: 'ngx-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.scss']
})
export class CalculatorComponent implements OnInit {

  constructor(private companyService: CompanyService, protected dateService: NbDateService<Date>, private buyService: BuyService

  ) { }
  sameDaySale: boolean;
  comRate: number;
  companyData: any;
  company: any;
  commissionRate: any;
  buyQty: number = 0;
  buyPrice: number = 0.00;
  sellPrice: number;
  avgPrice: number = 0.00;
  profitAmount: number = 0.00;
  profitPrecentage: number;
  costAmount: number;
  buyCommission: number;
  buyNetAmount: number;
  preQty: number = 0;
  preAvg: number = 0;
  preAmount: number = 0;
  availablePreviousStock: boolean;
  thisPurchaseAmount: number = 0;
  totPrice: number = 0.0;

  sellAmount: number;
  sellCommission: number;
  netSellingAmount: number;

  profitStarts = "success";

  ngOnInit(): void {
    this.loadCompanies();
    this.comRate = 1.12;
    this.commissionRate = this.comRate.toFixed(2) + ' %';
  }



  loadCompanies() {
    this.companyService.getCompaniesForDropDown().subscribe((companyD: any[]) => {
      this.companyData = companyD;
    });

  }

  loadCompanyStock() {
    this.buyService.getAvailableStock().subscribe((stock: any[]) => {

      let selectedCompany = stock.find(f => f.id == this.company);
      this.availablePreviousStock = false;
      this.preQty = 0;
      this.preAmount = 0;
      this.preAvg = 0;
      if (selectedCompany != undefined && selectedCompany != null) {
        this.availablePreviousStock = true;
        this.preQty = selectedCompany.qty;
        this.preAmount = selectedCompany.amount;
        this.preAvg = Number(selectedCompany.avgPrice.toFixed(2));
      }
      this.calculate();

    });
  }


  qtyCalculation() {
    let priceWithCommission = this.buyPrice + (this.buyPrice / 100 * this.comRate);
    let expectedQty = parseInt((this.totPrice / priceWithCommission).toString());
    this.buyQty = expectedQty;
    this.calculate();
  }

  calculate() {

    let _buyCommission: number;
    let _buyNetAmount: number;
    let _avgPrice: number = 0;
    let _sellAmount: number;
    let _sellCommission: number;
    let _netSellingAmount: number;
    let _profitAmount: number = 0;
    this.costAmount = this.buyPrice * this.buyQty;
    _buyCommission = this.costAmount / 100 * this.comRate;
    this.buyCommission = Number(_buyCommission.toFixed(2));

    this.thisPurchaseAmount = Number((this.costAmount + _buyCommission).toFixed(2));
    _buyNetAmount = this.costAmount + this.buyCommission + this.preAmount;
    this.buyNetAmount = Number(_buyNetAmount.toFixed(2));

    if (this.buyQty > 0 && this.buyPrice > 0) {
      _avgPrice = _buyNetAmount / (this.buyQty + this.preQty);
    }
    this.avgPrice = Number(_avgPrice.toFixed(2));

    if (this.preAvg > 0 && (this.buyQty == undefined || this.buyQty == null || this.buyQty <= 0) && (this.buyPrice == undefined || this.buyPrice == null || this.buyPrice <= 0)) {
      this.avgPrice = Number(this.preAvg.toFixed(2));
    }


    _sellAmount = this.sellPrice * (this.buyQty + this.preQty);

    this.sellAmount = Number(_sellAmount.toFixed(2));

    if (this.sameDaySale) {
      _sellCommission = 0;
      this.sellCommission = _sellCommission;
    } else {
      _sellCommission = _sellAmount / 100 * this.comRate
      this.sellCommission = Number(_sellCommission.toFixed(2));
    }

    _netSellingAmount = _sellAmount - _sellCommission;
    this.netSellingAmount = Number(_netSellingAmount.toFixed(2));

    _profitAmount = _netSellingAmount - _buyNetAmount;
    this.profitAmount = Number(_profitAmount.toFixed(2));
    this.profitPrecentage = Number(((_profitAmount / _buyNetAmount) * 100).toFixed(2));
    if (Number.isNaN(this.profitAmount)) {
      this.profitAmount = 0;
      this.profitPrecentage = 0;
    }

    if (_profitAmount < 0) {
      this.profitStarts = "danger";
    } else {
      this.profitStarts = "success";
    }

  }

}
