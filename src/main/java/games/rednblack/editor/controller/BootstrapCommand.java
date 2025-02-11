/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.controller;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import games.rednblack.editor.HyperLap2DFacade;
import games.rednblack.editor.controller.commands.AddComponentToItemCommand;
import games.rednblack.editor.controller.commands.AddSelectionCommand;
import games.rednblack.editor.controller.commands.AddToLibraryAction;
import games.rednblack.editor.controller.commands.AddToLibraryCommand;
import games.rednblack.editor.controller.commands.ChangeOriginPointPosition;
import games.rednblack.editor.controller.commands.ChangePolygonVertexPositionCommand;
import games.rednblack.editor.controller.commands.ChangeRulerPositionCommand;
import games.rednblack.editor.controller.commands.CompositeCameraChangeCommand;
import games.rednblack.editor.controller.commands.ConvertToButtonCommand;
import games.rednblack.editor.controller.commands.ConvertToCompositeCommand;
import games.rednblack.editor.controller.commands.CopyItemsCommand;
import games.rednblack.editor.controller.commands.CreateItemCommand;
import games.rednblack.editor.controller.commands.CreatePrimitiveCommand;
import games.rednblack.editor.controller.commands.CreateStickyNoteCommand;
import games.rednblack.editor.controller.commands.CustomVariableModifyCommand;
import games.rednblack.editor.controller.commands.CutItemsCommand;
import games.rednblack.editor.controller.commands.DeleteItemsCommand;
import games.rednblack.editor.controller.commands.DeleteLayerCommand;
import games.rednblack.editor.controller.commands.DeletePolygonVertexCommand;
import games.rednblack.editor.controller.commands.ExportProjectCommand;
import games.rednblack.editor.controller.commands.ItemChildrenTransformCommand;
import games.rednblack.editor.controller.commands.ItemTransformCommand;
import games.rednblack.editor.controller.commands.ItemsMoveCommand;
import games.rednblack.editor.controller.commands.LayerSwapCommand;
import games.rednblack.editor.controller.commands.ModifyStickyNoteCommand;
import games.rednblack.editor.controller.commands.NewLayerCommand;
import games.rednblack.editor.controller.commands.PasteItemsCommand;
import games.rednblack.editor.controller.commands.PluginItemCommand;
import games.rednblack.editor.controller.commands.ReleaseSelectionCommand;
import games.rednblack.editor.controller.commands.RemoveComponentFromItemCommand;
import games.rednblack.editor.controller.commands.RemoveStickyNoteCommand;
import games.rednblack.editor.controller.commands.RenameLayerCommand;
import games.rednblack.editor.controller.commands.SaveExportPathCommand;
import games.rednblack.editor.controller.commands.SetSelectionCommand;
import games.rednblack.editor.controller.commands.ShowNotificationCommand;
import games.rednblack.editor.controller.commands.UpdateEntityComponentsCommand;
import games.rednblack.editor.controller.commands.UpdateSceneDataCommand;
import games.rednblack.editor.controller.commands.component.ReplaceRegionCommand;
import games.rednblack.editor.controller.commands.component.ReplaceSpineCommand;
import games.rednblack.editor.controller.commands.component.ReplaceSpriteAnimationCommand;
import games.rednblack.editor.controller.commands.component.UpdateCompositeDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateImageItemDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateLabelDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateLightBodyDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateLightDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateParticleDataCommand;
import games.rednblack.editor.controller.commands.component.UpdatePhysicsDataCommand;
import games.rednblack.editor.controller.commands.component.UpdatePolygonDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateSensorDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateShaderDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateSpineDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateSpriteAnimationDataCommand;
import games.rednblack.editor.controller.commands.component.UpdateTalosDataCommand;
import games.rednblack.editor.controller.commands.resource.DeleteImageResource;
import games.rednblack.editor.controller.commands.resource.DeleteLibraryAction;
import games.rednblack.editor.controller.commands.resource.DeleteLibraryItem;
import games.rednblack.editor.controller.commands.resource.DeleteParticleEffect;
import games.rednblack.editor.controller.commands.resource.DeleteSpineAnimation;
import games.rednblack.editor.controller.commands.resource.DeleteSpriteAnimation;
import games.rednblack.editor.controller.commands.resource.DeleteTalosVFX;
import games.rednblack.editor.controller.commands.resource.DuplicateLibraryAction;
import games.rednblack.editor.controller.commands.resource.ExportActionCommand;
import games.rednblack.editor.controller.commands.resource.ExportLibraryItemCommand;
import games.rednblack.editor.splash.SplashScreenAdapter;
import games.rednblack.h2d.common.MsgAPI;

/**
 * Created by azakhary on 4/28/2015.
 */
public class BootstrapCommand extends SimpleCommand {

    @Override
	public void execute(INotification notification) {
        super.execute(notification);
        facade = HyperLap2DFacade.getInstance();
        facade.sendNotification(SplashScreenAdapter.UPDATE_SPLASH, "Loading Commands...");

        facade.registerCommand(MsgAPI.ACTION_CUT, CutItemsCommand::new);
        facade.registerCommand(MsgAPI.ACTION_COPY, CopyItemsCommand::new);
        facade.registerCommand(MsgAPI.ACTION_PASTE, PasteItemsCommand::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE, DeleteItemsCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CREATE_ITEM, CreateItemCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CAMERA_CHANGE_COMPOSITE, CompositeCameraChangeCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CREATE_PRIMITIVE, CreatePrimitiveCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CREATE_STICKY_NOTE, CreateStickyNoteCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CREATE_STICKY_NOTE, CreateStickyNoteCommand::new);
        facade.registerCommand(MsgAPI.ACTION_REMOVE_STICKY_NOTE, RemoveStickyNoteCommand::new);
        facade.registerCommand(MsgAPI.ACTION_MODIFY_STICKY_NOTE, ModifyStickyNoteCommand::new);

        facade.registerCommand(MsgAPI.ACTION_DELETE_LAYER, DeleteLayerCommand::new);
        facade.registerCommand(MsgAPI.ACTION_NEW_LAYER, NewLayerCommand::new);
        facade.registerCommand(MsgAPI.ACTION_SWAP_LAYERS, LayerSwapCommand::new);
        facade.registerCommand(MsgAPI.ACTION_RENAME_LAYER, RenameLayerCommand::new);

        facade.registerCommand(MsgAPI.ACTION_ADD_COMPONENT, AddComponentToItemCommand::new);
        facade.registerCommand(MsgAPI.ACTION_REMOVE_COMPONENT, RemoveComponentFromItemCommand::new);
        facade.registerCommand(MsgAPI.CUSTOM_VARIABLE_MODIFY, CustomVariableModifyCommand::new);

        facade.registerCommand(MsgAPI.ACTION_ITEMS_MOVE_TO, ItemsMoveCommand::new);

        facade.registerCommand(MsgAPI.ACTION_ITEM_AND_CHILDREN_TO, ItemChildrenTransformCommand::new);

        facade.registerCommand(MsgAPI.ACTION_ITEM_TRANSFORM_TO, ItemTransformCommand::new);
        facade.registerCommand(MsgAPI.ACTION_REPLACE_REGION_DATA, ReplaceRegionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_REPLACE_SPRITE_ANIMATION_DATA, ReplaceSpriteAnimationCommand::new);
        facade.registerCommand(MsgAPI.ACTION_REPLACE_SPINE_ANIMATION_DATA, ReplaceSpineCommand::new);
        facade.registerCommand(MsgAPI.ACTION_ADD_TO_LIBRARY, AddToLibraryCommand::new);
        facade.registerCommand(MsgAPI.ACTION_ADD_TO_LIBRARY_ACTION, AddToLibraryAction::new);
        facade.registerCommand(MsgAPI.ACTION_CONVERT_TO_BUTTON, ConvertToButtonCommand::new);
        facade.registerCommand(MsgAPI.ACTION_GROUP_ITEMS, ConvertToCompositeCommand::new);

        facade.registerCommand(MsgAPI.ACTION_SET_SELECTION, SetSelectionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_ADD_SELECTION, AddSelectionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_RELEASE_SELECTION, ReleaseSelectionCommand::new);

        facade.registerCommand(MsgAPI.ACTION_UPDATE_RULER_POSITION, ChangeRulerPositionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CHANGE_POLYGON_VERTEX_POSITION, ChangePolygonVertexPositionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_POLYGON_VERTEX, DeletePolygonVertexCommand::new);
        facade.registerCommand(MsgAPI.ACTION_CHANGE_ORIGIN_POSITION, ChangeOriginPointPosition::new);

        // DATA MODIFY by components
        facade.registerCommand(MsgAPI.ACTION_UPDATE_SCENE_DATA, UpdateSceneDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_ITEM_DATA, UpdateEntityComponentsCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_LABEL_DATA, UpdateLabelDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_LIGHT_DATA, UpdateLightDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_COMPOSITE_DATA, UpdateCompositeDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_PARTICLE_DATA, UpdateParticleDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_TALOS_DATA, UpdateTalosDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_BODY_LIGHT_DATA, UpdateLightBodyDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_PHYSICS_BODY_DATA, UpdatePhysicsDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_SENSOR_DATA, UpdateSensorDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_SHADER_DATA, UpdateShaderDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_IMAGE_ITEM_DATA, UpdateImageItemDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_SPRITE_ANIMATION_DATA, UpdateSpriteAnimationDataCommand::new);
        facade.registerCommand(MsgAPI.ACTION_UPDATE_SPINE_ANIMATION_DATA, UpdateSpineDataCommand::new);

        facade.registerCommand(MsgAPI.ACTION_UPDATE_MESH_DATA, UpdatePolygonDataCommand::new);

        facade.registerCommand(MsgAPI.ACTION_EXPORT_PROJECT, ExportProjectCommand::new);
        facade.registerCommand(MsgAPI.SAVE_EXPORT_PATH, SaveExportPathCommand::new);

        facade.registerCommand(MsgAPI.ACTION_PLUGIN_PROXY_COMMAND, PluginItemCommand::new);

        // Resources
        facade.registerCommand(MsgAPI.ACTION_DELETE_IMAGE_RESOURCE, DeleteImageResource::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_LIBRARY_ITEM, DeleteLibraryItem::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_LIBRARY_ACTION, DeleteLibraryAction::new);
        facade.registerCommand(MsgAPI.ACTION_DUPLICATE_LIBRARY_ACTION, DuplicateLibraryAction::new);
        facade.registerCommand(MsgAPI.ACTION_EXPORT_LIBRARY_ITEM, ExportLibraryItemCommand::new);
        facade.registerCommand(MsgAPI.ACTION_EXPORT_ACTION_ITEM, ExportActionCommand::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_PARTICLE_EFFECT, DeleteParticleEffect::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_TALOS_VFX, DeleteTalosVFX::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_SPINE_ANIMATION_RESOURCE, DeleteSpineAnimation::new);
        facade.registerCommand(MsgAPI.ACTION_DELETE_SPRITE_ANIMATION_RESOURCE, DeleteSpriteAnimation::new);

        facade.registerCommand(MsgAPI.SHOW_NOTIFICATION, ShowNotificationCommand::new);
    }
}
