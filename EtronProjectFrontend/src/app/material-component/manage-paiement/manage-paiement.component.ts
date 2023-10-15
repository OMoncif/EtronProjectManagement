import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { PaiementService } from 'src/app/services/paiement.service';

@Component({
  selector: 'app-manage-paiement',
  templateUrl: './manage-paiement.component.html',
  styleUrls: ['./manage-paiement.component.scss']
})
export class ManagePaiementComponent {

  displayedColumns: string[] = ['datePaiement', 'montant', 'user'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
  responseMessage: any;

  constructor(
    private ngxService: NgxUiLoaderService,
    private paiementservice: PaiementService,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.paiementservice.getPaiements().subscribe(
      (response: any) => {
        this.ngxService.stop();
        console.log('API Response:', response);
        this.dataSource.data = response; // Update the data property of the MatTableDataSource
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  

  
  

}
