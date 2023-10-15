import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { VoitureService } from 'src/app/services/voiture.service';
import { VoitureComponent } from '../dialog/voiture/voiture.component';

@Component({
  selector: 'app-manage-voiture',
  templateUrl: './manage-voiture.component.html',
  styleUrls: ['./manage-voiture.component.scss']
})
export class ManageVoitureComponent {

  displayedColumns: string[] = ['Modele','dateAjoutVoiture'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
  responseMessage: any;

  constructor(
    private ngxService: NgxUiLoaderService,
    private voitureService: VoitureService,
    private snackbarService: SnackbarService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.voitureService.getVoitures().subscribe(
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

  handleEditAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      action: 'Edit',
      data: values
    };
    dialogConfig.width = '850px';
    const dialogRef = this.dialog.open(VoitureComponent, dialogConfig);
    this.router.events.subscribe(() => {
      dialogRef.close();
    });

    dialogRef.componentInstance?.onEditVoiture.subscribe((response) => {
      this.tableData();
    });
  }

  handleDeleteAction(values: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message: 'delete ' + values.name + ' User',
      confirmation: true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
    dialogRef.componentInstance?.onEmitStatusChange.subscribe((response) => {
      this.ngxService.start();
      this.delete(values.id);
      dialogRef.close();
    });
  }
  delete(id: any) {
    this.voitureService.delete(id).subscribe(
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

      this.voitureService.update(data).subscribe(
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
