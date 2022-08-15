import { Component, OnInit } from '@angular/core';
import { ServiceService } from 'app/services/service.service';
import { Chart, registerables } from 'chart.js';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-transaction-chart',
  templateUrl: './transaction-chart.component.html',
  styleUrls: ['./transaction-chart.component.css']
})
export class TransactionChartComponent implements OnInit {
  result: any;
  transactionAmount: any;
  transactionDate: any;
  dateNew: any;
  chart: any = [];

  constructor(private service: ServiceService) {
    Chart.register(...registerables);
  }

  ngOnInit() {
    this.service.getTransactions().subscribe(res => {
      this.result = res;
      this.transactionAmount = this.result.map((transactions: any) => transactions.amount);
      this.transactionDate = this.result.map((transactions: any) => formatDate(transactions.transactionDate, 'mediumDate', 'en-us', '+530'));

      this.chart = new Chart('canvas', {
        type: 'line',
        data: {
          labels: this.transactionDate,
          datasets: [
            {
              data: this.transactionAmount,
              borderColor: '#3e95cd',
              fill: false,
              label: 'Amount evolution',
              tension: 0.3,
              backgroundColor: '#B8C7DB',
              borderWidth: 4,

            },
          ],
        },
      });
    });
  }
}