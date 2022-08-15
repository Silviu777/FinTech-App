import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../services/service.service';
import { formatDate } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';


@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
    depositMoneyForm: FormGroup;

    accountNo: string;
    balance: number;
    currency: string;
    interestRate: number;
    dateOpened: Date;
    parsedDateOpened: string;

    constructor(
        private service: ServiceService,
        private toastr: ToastrService,
    ) {
    }

    ngOnInit(): void {
        this.fetchAccountDetails();
        this.depositMoneyForm = new FormGroup({
            amount: new FormControl(null, Validators.required)
        });
    }

    private fetchAccountDetails() {
        this.service.onGetAccount().subscribe(
            response => {
                this.accountNo = response['iban'];
                this.balance = response['balance'];
                this.currency = response['currency'];
                this.interestRate = response['interestRate'];
                this.dateOpened = new Date(response['dateOpened']);
                this.parsedDateOpened = formatDate(this.dateOpened, 'mediumDate', 'en-us', '+530');
            }
        );
    }

    depositMoneyFormSubmit() {
        const depositDetails = {
            amount: this.depositMoneyForm.value.amount,

        };
        this.service.depositMoney(depositDetails).subscribe(
            response => {
                this.toastr.success(response['message']);
            }
        )
    }
}
