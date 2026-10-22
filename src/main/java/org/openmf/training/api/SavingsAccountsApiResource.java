package org.openmf.training.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.openmf.training.data.*;
import org.openmf.training.service.SavingsAccountAuditTrailWritePlatformService;
import org.openmf.training.service.SavingsAccountWritePlatformService;
import org.openmf.training.service.SavingsAccountNoteWritePlatformService;
import org.openmf.training.service.StandingInstructionReadPlatformService;
import org.openmf.training.service.StandingInstructionWritePlatformService;
import org.springframework.stereotype.Component;

@Component
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class SavingsAccountsApiResource {

    private final SavingsAccountWritePlatformService accountService;
    private final SavingsAccountNoteWritePlatformService noteService;
    private final SavingsAccountAuditTrailWritePlatformService auditTrailService;
    private final StandingInstructionReadPlatformService standingInstructionService;
    private final StandingInstructionWritePlatformService standingInstructionWriteService;

    public SavingsAccountsApiResource(
            SavingsAccountWritePlatformService accountService,
            SavingsAccountNoteWritePlatformService noteService,
            SavingsAccountAuditTrailWritePlatformService auditTrailService,
            StandingInstructionReadPlatformService standingInstructionService,
            StandingInstructionWritePlatformService standingInstructionWriteService
    ) {
        this.accountService = accountService;
        this.noteService = noteService;
        this.auditTrailService = auditTrailService;
        this.standingInstructionService = standingInstructionService;
        this.standingInstructionWriteService = standingInstructionWriteService;
    }

    @GET
    @Path("savings-accounts")
    public List<SavingsAccountData> retrieveAll() {
        return accountService.retrieveAll();
    }

    @GET
    @Path("savings-accounts/{accountId}/holds")
    public List<SavingsAccountHoldData> retrieveHolds(@PathParam("accountId") Long accountId) {
        return accountService.retrieveHolds(accountId);
    }

    @GET
    @Path("savings-accounts/{accountId}/notes")
    public List<SavingsAccountNoteData> retrieveNotes(
            @PathParam("accountId") Long accountId
    ) {
        return noteService.retrieveByAccount(accountId);
    }

    @POST
    @Path("savings-accounts/{accountId}/notes")
    public SavingsAccountNoteData createNote(
            @PathParam("accountId") Long accountId,
            CreateSavingsAccountNoteRequest request
    ) {
        return noteService.create(accountId, request);
    }

    @DELETE
    @Path("savings-accounts/{accountId}/notes/{noteId}")
    public void deleteNote(
            @PathParam("accountId") Long accountId,
            @PathParam("noteId") Long noteId
    ) {
        noteService.delete(accountId, noteId);
    }

    @GET
    @Path("savings-accounts/{accountId}/audit-trail")
    public List<SavingsAccountAuditTrailData> retrieveAuditTrail(
            @PathParam("accountId") Long accountId
    ) {
        return auditTrailService.retrieveByAccount(accountId);
    }

    @GET
    @Path("savings-accounts/{accountId}/standing-instructions")
    public List<StandingInstructionData> retrieveStandingInstructions(
            @PathParam("accountId") Long accountId
    ) {
        return standingInstructionService.retrieveByFromAccount(accountId);
    }

    @POST
    @Path("savings-accounts/{accountId}/standing-instructions")
    public StandingInstructionData createStandingInstruction(
            @PathParam("accountId") Long accountId,
            CreateStandingInstructionRequest request
    ) {
        return standingInstructionWriteService.create(accountId, request);
    }

    @POST
    @Path("savings-accounts/{accountId}/standing-instructions/{instructionId}/{action}")
    public StandingInstructionData updateStandingInstructionStatus(
            @PathParam("accountId") Long accountId,
            @PathParam("instructionId") Long instructionId,
            @PathParam("action") String action
    ) {
        return standingInstructionWriteService.updateStatus(
                accountId,
                instructionId,
                action
        );
    }

    @POST
    @Path("savings-accounts")
    public SavingsAccountData create(
            CreateSavingsAccountRequest request
    ) {
        return accountService.create(request);
    }

    @POST
    @Path("savings-accounts/{accountId}/holds")
    public SavingsAccountHoldData placeHold(@PathParam("accountId") Long accountId, CreateSavingsAccountHoldRequest request) {
        return accountService.placeHold(accountId, request);
    }

    @POST
    @Path("savings-accounts/{accountId}/holds/{holdId}/release")
    public SavingsAccountHoldData releaseHold(@PathParam("accountId") Long accountId, @PathParam("holdId") Long holdId) {
        return accountService.releaseHold(accountId, holdId);
    }

    @POST
    @Path("savings-accounts/{accountId}")
    public SavingsAccountData handleCommand(
            @PathParam("accountId") Long accountId,
            @QueryParam("command") String command
    ) {
        return accountService.handleCommand(accountId, command);
    }

    @PUT
    @Path("savings-accounts/{accountId}")
    public SavingsAccountData updateAccountNo(@PathParam("accountId") Long accountId, UpdateAccountNoRequest updateAccountNoRequest) {
        return accountService.updateAccountNo(accountId, updateAccountNoRequest);
    }
}