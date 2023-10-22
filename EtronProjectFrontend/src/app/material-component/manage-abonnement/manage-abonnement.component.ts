import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { AbonnementService } from 'src/app/services/abonnement.service';
import { AbonnementComponent } from '../dialog/abonnement/abonnement.component';

@Component({
  selector: 'app-manage-abonnement',
  templateUrl: './manage-abonnement.component.html',
  styleUrls: ['./manage-abonnement.component.scss']
})
export class ManageAbonnementComponent {

  displayedColumns: string[] = ['type', 'fraismois', 'dureeContrat', 'fraisAC', 'fraisDCHPC', 'fraisHautePuissance','fraisBlocageAC','fraisBlocageDC'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
  responseMessage: any;

  constructor(
    private ngxService: NgxUiLoaderService,
    private abonnementService: AbonnementService,
    private snackbarService: SnackbarService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.abonnementService.getPlans().subscribe(
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

  handleAddAction() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Add'
    };
    dialogConfig.width = "850px";
    const dialogRef = this.dialog.open(AbonnementComponent,dialogConfig);
    this.router.events.subscribe(()=>{
      dialogRef.close();
    });
    const sub = dialogRef.componentInstance.onAddAbonnement.subscribe((response)=>{
      this.tableData();
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  handleEditAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Edit',
      data: values
    };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(AbonnementComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    dialogRef.componentInstance?.onEditAbonnement.subscribe((response) => {
      this.tableData();
    });
  }

  handleDeleteAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'delete ' + values.type + ' Abonnement',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    dialogRef.componentInstance?.onEmitStatusChange.subscribe((response) => {
      this.ngxService.start();
      this.delete(values.type);
      dialogRef.close();
    });
  }
  delete(type: any) {
    this.abonnementService.delete(type).subscribe(
      (response: any) => {
        this.ngxService.stop();
        this.tableData();
        this.responseMessage = response?.message;
        this.snackbarService.openSnackBar(this.responseMessage, 'success');
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

  
  onChange(event: Event, id: any) {
    this.ngxService.start();
    const target = event.target as HTMLInputElement | null;

    if (target?.checked !== undefined) {
      const status = target.checked.toString();
      const data = {
        status: status,
        id: id
      };

      this.abonnementService.update(data).subscribe(
        (response: any) => {
          this.ngxService.stop();
          this.responseMessage = response?.message;
          this.snackbarService.openSnackBar(this.responseMessage, 'success');
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
  }
}
