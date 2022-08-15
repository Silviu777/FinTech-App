import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ServiceService } from '../services/service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-transfer-money',
  templateUrl: './transfer-money.component.html',
  styleUrls: ['./transfer-money.component.css']
})
export class TransferMoneyComponent implements OnInit {
  transferMoneyForm: FormGroup;

  constructor(
    private service: ServiceService,
    private toastr: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.transferMoneyForm = new FormGroup(
      {
        receiverIban: new FormControl(null, Validators.required),
        amount: new FormControl(null, Validators.required),
        currency: new FormControl(null, Validators.required),
        description: new FormControl(null, Validators.required)

      }
    );
  }

  transferMoneyFormSubmit() {
    const transferDetails = {
      receiverIban: this.transferMoneyForm.value.receiverIban,
      amount: this.transferMoneyForm.value.amount,
      currency: this.transferMoneyForm.value.currency,
      description: this.transferMoneyForm.value.description,
    };
    this.service.transferMoney(transferDetails).subscribe(
      response => {
        this.toastr.success(response['message']);
      }
    )
  }
}
