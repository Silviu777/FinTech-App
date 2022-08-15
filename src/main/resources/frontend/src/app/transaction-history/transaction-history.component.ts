import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../services/service.service';
import { formatDate } from '@angular/common';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {
  transactions: any;
  constructor(private service: ServiceService) {
  }

  ngOnInit(): void {
    this.getTransactions();

  }

  public printTransactionHistoryPdf(): void {
    let data: any = document.getElementById('htmlData');
    html2canvas(data).then((canvas) => {

      let fileWidth = 210;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      let pdf = new jsPDF('p', 'mm', 'a4');
      const FILEURI = canvas.toDataURL('image/png');

      let position = 0;
      pdf.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight);
      pdf.save('transaction-history.pdf');

    });
  }

  getTransactions() {
    this.service.getTransactions().subscribe(
      response => {

        this.transactions = response;
        console.log(this.transactions);
      }
    );
  }

  formatDate(date) {
    return formatDate(date, 'mediumDate', 'en-us', '+530');
  }
}
