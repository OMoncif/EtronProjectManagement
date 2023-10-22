import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { FactureService } from 'src/app/services/facture.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { DemandeFactureComponent } from '../dialog/demande-facture/demande-facture.component';
import { Router } from '@angular/router';
import { EtronService } from 'src/app/services/etron.service';

@Component({
  selector: 'app-manage-facture',
  templateUrl: './manage-facture.component.html',
  styleUrls: ['./manage-facture.component.scss']
})
export class ManageFactureComponent {

  displayedColumns: string[] = ['dateFacturation', 'montantTotal', 'status', 'userEmail'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
  responseMessage: any;

  constructor(
    private ngxService: NgxUiLoaderService,
    private factureservice: FactureService,
    private snackbarService: SnackbarService,
    private etronService: EtronService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.factureservice.getFactures().subscribe(
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

  handleAddAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Add'
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(DemandeFactureComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddFacture.subscribe((response) => {
      this.tableData();
    })
  }

  payerFacture() {
    
    this.etronService.payerFacture().subscribe(
      (response: any) => {
        this.responseMessage = response;
      },
      (error: any) => {
        this.responseMessage = error.error?.message;

        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );

  }

}
