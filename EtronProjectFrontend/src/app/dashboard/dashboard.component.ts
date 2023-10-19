import { Component, AfterViewInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { DashboardService } from '../services/dashboard.service';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';
import { MatTableDataSource } from '@angular/material/table';
import { ContratService } from '../services/contrat.service';
import { AbonnementService } from '../services/abonnement.service';
import { UserService } from '../services/user.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmationComponent } from '../material-component/dialog/confirmation/confirmation.component';
import { UserComponent } from '../material-component/dialog/user/user.component';
import { Router } from '@angular/router';
import { AbonnementComponent } from '../material-component/dialog/abonnement/abonnement.component';
@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['../../assets/style.css']
})
export class DashboardComponent implements AfterViewInit {

	dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
	responseMessage: any;

	dataSource1: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
	responseMessage1: any;

	dataSource2: MatTableDataSource<any> = new MatTableDataSource<any>([]); // Initialize with an empty array
	responseMessage2: any;

	data: any;
	ngAfterViewInit() { }

	constructor(private dashboardService: DashboardService,
		private ngxService: NgxUiLoaderService,
		private contratservice: ContratService,
		private abonnementservice: AbonnementService,
		private userservice: UserService,
		private snackbarService: SnackbarService,
		private dialog: MatDialog,
		private router: Router) {
		this.ngxService.start();
		this.dashboardData();
	}

	ngOnInit(): void {
		this.ngxService.start();
		this.tableData();
		this.tableData1();
		this.tableData2();
	}

	tableData() {
		this.contratservice.getContrats().subscribe(
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

	tableData1() {
		this.abonnementservice.getPlans().subscribe(
			(response: any) => {
				this.ngxService.stop();
				console.log('API Response:', response);
				this.dataSource1.data = response; // Update the data property of the MatTableDataSource
			},
			(error: any) => {
				this.ngxService.stop();
				console.log(error);
				if (error.error?.message) {
					this.responseMessage1 = error.error?.message;
				} else {
					this.responseMessage1 = GlobalConstants.genericError;
				}
				this.snackbarService.openSnackBar(this.responseMessage1, GlobalConstants.error);
			}
		);
	}

	tableData2() {
		this.userservice.getUsers().subscribe(
			(response: any) => {
				this.ngxService.stop();
				console.log('API Response:', response);
				this.dataSource2.data = response; // Update the data property of the MatTableDataSource
			},
			(error: any) => {
				this.ngxService.stop();
				console.log(error);
				if (error.error?.message) {
					this.responseMessage2 = error.error?.message;
				} else {
					this.responseMessage2 = GlobalConstants.genericError;
				}
				this.snackbarService.openSnackBar(this.responseMessage2, GlobalConstants.error);
			}
		);
	}

	dashboardData() {
		this.dashboardService.getDetails().subscribe((response: any) => {
			this.ngxService.stop();
			this.data = response;
		}, (error: any) => {
			this.ngxService.stop();
			console.log(error);
			if (error.error?.message) {
				this.responseMessage = error.error?.message;
			}
			else {
				this.responseMessage = GlobalConstants.genericError;
			}
			this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
		})
	}

	handleEditUserAction(values: any) {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.data = {
			action: 'Edit',
			data: values
		};
		dialogConfig.width = '850px';
		const dialogRef = this.dialog.open(UserComponent, dialogConfig);
		this.router.events.subscribe(() => {
			dialogRef.close();
		});

		dialogRef.componentInstance?.onEditUser.subscribe((response) => {
			this.tableData2();
		});
	}

	handleDeleteUserAction(values: any) {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.data = {
			message: 'delete ' + values.name + ' User',
			confirmation: true
		};
		const dialogRef = this.dialog.open(ConfirmationComponent, dialogConfig);
		dialogRef.componentInstance?.onEmitStatusChange.subscribe((response) => {
			this.ngxService.start();
			this.deleteUser(values.id);
			dialogRef.close();
		});
	}
	deleteUser(id: any) {
		this.userservice.delete(id).subscribe(
			(response: any) => {
				this.ngxService.stop();
				this.tableData2();
				this.responseMessage2 = response?.message;
				this.snackbarService.openSnackBar(this.responseMessage2, 'success');
			},
			(error: any) => {
				this.ngxService.stop();
				console.log(error);
				if (error.error?.message) {
					this.responseMessage2 = error.error?.message;
				} else {
					this.responseMessage2 = GlobalConstants.genericError;
				}
				this.snackbarService.openSnackBar(this.responseMessage2, GlobalConstants.error);
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

			this.userservice.update(data).subscribe(
				(response: any) => {
					this.ngxService.stop();
					this.responseMessage2 = response?.message;
					this.snackbarService.openSnackBar(this.responseMessage2, 'success');
				},
				(error: any) => {
					this.ngxService.stop();
					console.log(error);
					if (error.error?.message) {
						this.responseMessage2 = error.error?.message;
					} else {
						this.responseMessage2 = GlobalConstants.genericError;
					}
					this.snackbarService.openSnackBar(this.responseMessage2, GlobalConstants.error);
				}
			);
		}
	}

	handleAddUserAction() {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.data = {
			action: 'Add'
		};
		dialogConfig.width = "850px";
		const dialogRef = this.dialog.open(UserComponent, dialogConfig);
		this.router.events.subscribe(() => {
			dialogRef.close();
		});
		const sub = dialogRef.componentInstance.onAddUser.subscribe((response) => {
			this.tableData2();
		})
	}

	handleAddAction() {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.data = {
			action: 'Add'
		};
		dialogConfig.width = "850px";
		const dialogRef = this.dialog.open(AbonnementComponent, dialogConfig);
		this.router.events.subscribe(() => {
			dialogRef.close();
		});
		const sub = dialogRef.componentInstance.onAddAbonnement.subscribe((response) => {
			this.tableData();
		})
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
			this.delete(values.id);
			dialogRef.close();
		});
	}
	delete(id: any) {
		this.abonnementservice.delete(id).subscribe(
			(response: any) => {
				this.ngxService.stop();
				this.tableData1();
				this.responseMessage1 = response?.message;
				this.snackbarService.openSnackBar(this.responseMessage1, 'success');
			},
			(error: any) => {
				this.ngxService.stop();
				console.log(error);
				if (error.error?.message) {
					this.responseMessage1 = error.error?.message;
				} else {
					this.responseMessage1 = GlobalConstants.genericError;
				}
				this.snackbarService.openSnackBar(this.responseMessage1, GlobalConstants.error);
			}
		);
	}


	onChangePlan(event: Event, id: any) {
		this.ngxService.start();
		const target = event.target as HTMLInputElement | null;

		if (target?.checked !== undefined) {
			const status = target.checked.toString();
			const data = {
				status: status,
				id: id
			};

			this.abonnementservice.update(data).subscribe(
				(response: any) => {
					this.ngxService.stop();
					this.responseMessage1 = response?.message;
					this.snackbarService.openSnackBar(this.responseMessage1, 'success');
				},
				(error: any) => {
					this.ngxService.stop();
					console.log(error);
					if (error.error?.message) {
						this.responseMessage1 = error.error?.message;
					} else {
						this.responseMessage1 = GlobalConstants.genericError;
					}
					this.snackbarService.openSnackBar(this.responseMessage1, GlobalConstants.error);
				}
			);
		}
	}

}
