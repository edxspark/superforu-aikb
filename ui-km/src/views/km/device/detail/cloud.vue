<template>
  <div class="cloudIndex-div">
    <div class="a-tree">
      <a-space style="margin-left: 80%; margin-bottom: 5px">
        <div>
          <a-button size="mini" type="dashed" @click="addRootNode">
            <template #icon>
              <icon-plus />
            </template>
            根目录
          </a-button>
        </div>
      </a-space>

      <a-tree
        v-if="treeData.length"
        v-model:selectedKeys="selectedKeys"
        class="tree-demo"
        draggable
        block-node
        :checkable="checked"
        :data="treeData"
        @select="getSelectedNodes"
        @drop="onDrop"
        @drag-end="dragEnd"
      >
        <template #extra="nodeData">
          <IconPlus
            v-if="selectedKeys.includes(nodeData.key)"
            style="
              position: absolute;
              right: -20px;
              font-size: 12px;
              top: 10px;
              color: #3370ff;
            "
            @click="() => onIconClick(nodeData)"
          />
          <a-space size="large">
            <a-dropdown position="br" :hide-on-select="false">
              <IconMore
                v-if="selectedKeys.includes(nodeData.key)"
                style="
                  position: absolute;
                  right: -40px;
                  font-size: 12px;
                  top: 10px;
                  color: #3370ff;
                "
              />
              <template #content>
                <a-doption v-for="link in links" :key="link.text">
                  <a-popconfirm
                    v-if="link.text === 'workplace.delete'"
                    :content="$t('workplace.deleteIs')"
                    @ok="deleteItem(nodeData)"
                  >
                    <div class="dropdown-div">
                      <div class="icon">
                        <component :is="link.icon" style="color: #949ba4" />
                      </div>
                      <span class="text">
                        {{ $t(link.text) }}
                      </span>
                    </div>
                  </a-popconfirm>
                  <div
                    v-else
                    class="dropdown-div"
                    @click="() => renameClick(nodeData)"
                  >
                    <div class="icon">
                      <component :is="link.icon" style="color: #949ba4" />
                    </div>
                    <span class="text">
                      {{ $t(link.text) }}
                    </span>
                  </div>
                </a-doption>
              </template>
            </a-dropdown>
          </a-space>
        </template>
      </a-tree>
    </div>
    <div class="menu-search">
      <a-input
        v-model="renameInputValue"
        class="search"
        :style="{ width: '50%' }"
        size="small"
        @blur="renameBlurClick"
      />
      <span style="font-size: 12px; color: #3ba561">{{
        $t('km.doc.save.status')
      }}</span>
      <iframe
        :src="dynamicSrc"
        width="100%"
        height="100%"
        frameborder="0"
      ></iframe>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, defineProps, watchEffect } from 'vue';
  import { Message, TreeNodeData } from '@arco-design/web-vue';
  import {
    listTreeData,
    updateTreeData,
    addFolderFile
    // deleteFolderFile
  } from '@/api/dashboard/index';

  const props = defineProps({
    itemName: { type: String, required: true },
    itemId: { type: String, required: true }
  });

  const defaultTreeData = [];

  const links = [
    // {
    //   text: 'workplace.rename',
    //   icon: 'icon-reply',
    // },
    {
      text: 'workplace.delete',
      icon: 'icon-minus-circle'
    }
  ];
  const selectedFileId = ref('');
  const renameInputValue = ref('');
  const selectedKeys = ref<string[]>([]);
  const treeData = ref([]);
  const checked = ref(false);
  const findDeleteNodeResult = ref(false);

  const onChildClick = async () => {
    const params = {
      id: props.itemId
    };

    // @ts-ignore
    const res = await listTreeData(params);
    // @ts-ignore
    const { fileKmTreeData } = res.data;
    treeData.value = fileKmTreeData
      ? JSON.parse(fileKmTreeData)
      : defaultTreeData;
  };

  const dynamicSrc = ref('');
  // onMounted(() => {
  //   onChildClick();
  // });
  watchEffect(() => {
    if (props.itemName) {
      onChildClick();
    }
    if (selectedFileId.value) {
      dynamicSrc.value = `${localStorage.getItem(
        'spf_file_editors_host'
      )}?server=content&mode=edit&fileId=${selectedFileId.value}&kmType=km`;
    }
  });

  const updateTree = async () => {
    const params = {
      id: props.itemId,
      fileKmTreeData: JSON.stringify(treeData.value)
    };
    // @ts-ignore
    await updateTreeData(params);
    await onChildClick();
  };

  // 查找树形结构节点
  const findNodeByKey = (data: any[], key: string): any | null => {
    const stack = [...data];
    while (stack.length > 0) {
      const node = stack.pop();
      if (node.key === key) {
        return node;
      }
      if (node.children) {
        stack.push(...node.children);
      }
    }
    return null;
  };

  const renameBlurClick = () => {
    if (renameInputValue.value === '') {
      Message.error('名称不能为空');
    } else {
      const nodeToUpdate = findNodeByKey(treeData.value, selectedKeys.value[0]);
      if (nodeToUpdate) {
        nodeToUpdate.title = renameInputValue.value;
      }
      treeData.value = [...treeData.value];
      updateTree();
    }
  };

  // 删除目标节点根据key
  const deleteTreeNodeByKey = (tree: any[], key: string): any[] => {
    return tree.filter((node: any) => {
      if (node.key === key) {
        return false;
      }
      if (node.children) {
        node.children = deleteTreeNodeByKey(node.children, key);
      }
      return true;
    });
  };

  const deleteItem = (nodeData: { key: string }) => {
    const keyToDelete = nodeData.key;
    treeData.value = deleteTreeNodeByKey(treeData.value, keyToDelete);
    updateTree();
  };

  // const getSelectedNodes = (
  //   newSelectedKeys: any,
  //   event: { node: { title: string; id?: string | number } }
  // ) => {
  //   renameInputValue.value = event.node.title;
  //   // @ts-ignore
  //   selectedFileId.value = event.node.id ?? null;
  // };
  const getSelectedNodes = (
    newSelectedKeys: (string | number)[],
    data: {
      selected?: boolean;
      selectedNodes: TreeNodeData[];
      node?: TreeNodeData;
      e?: Event;
    }
  ) => {
    if (data.node) {
      // @ts-ignore
      renameInputValue.value = data.node.title;
      // @ts-ignore
      selectedFileId.value = data.node.id ?? null;
    }
  };

  const renameClick = (nodeData: { title: string }) => {
    renameInputValue.value = nodeData.title;
    // renameDisabled.value = false;
  };

  const addRootNode = async nodeData => {
    const timestamp = new Date().getTime();
    const docName = timestamp.toString().substring(9);
    const params = {
      fileName: `未命名${docName}`,
      parentId: '0',
      linkKmId: props.itemId,
      linkFileTypeCode: 'markdown',
      linkFileTypeName: '在线文档'
    };

    // @ts-ignore
    const res = await addFolderFile(params);

    // 添加到根目录
    const nodeKey = `${res.data}`;
    const rootNode = {
      title: `未命名${docName}`,
      id: res.data,
      key: nodeKey
    };

    const children = nodeData.children || [];
    children.push(rootNode);
    nodeData.children = children;
    treeData.value = [rootNode, ...treeData.value];
    selectedKeys.value = [nodeKey];

    await updateTree();
  };

  // @ts-ignore
  const onIconClick = async nodeData => {
    const timestamp = new Date().getTime();
    const docName = timestamp.toString().substring(9);
    const params = {
      fileName: `未命名${docName}`,
      parentId: '0',
      linkKmId: props.itemId,
      linkFileTypeCode: 'markdown',
      linkFileTypeName: '在线文档'
    };
    // @ts-ignore
    const res = await addFolderFile(params);
    const children = nodeData.children || [];
    children.push({
      title: `未命名${docName}`,
      id: res.data,
      key: res.data
    });
    nodeData.children = children;
    treeData.value = [...treeData.value];

    const selectKey = `"${res.data}"`;
    selectedKeys.value = [selectKey];

    await updateTree();
  };
  const dragEnd = () => {
    updateTree();
  };
  // @ts-ignore
  const onDrop = ({ dragNode, dropNode, dropPosition }) => {
    const data = treeData.value;
    // @ts-ignore
    const loop = (datas, key, callback) => {
      datas.some((item: { key: any; children: any }, index: any, arr: any) => {
        if (item.key === key) {
          callback(item, index, arr);
          return true;
        }
        if (item.children) {
          return loop(item.children, key, callback);
        }
        return false;
      });
    };

    loop(data, dragNode.key, (_: any, index: any, arr: any[]) => {
      arr.splice(index, 1);
    });

    if (dropPosition === 0) {
      loop(data, dropNode.key, (item: { children: any[] }) => {
        item.children = item.children || [];
        item.children.push(dragNode);
      });
    } else {
      loop(data, dropNode.key, (_: any, index: number, arr: any[]) => {
        arr.splice(dropPosition < 0 ? index : index + 1, 0, dragNode);
      });
    }
  };
</script>

<style lang="less" scoped>
  .cloudIndex-div {
    display: flex;
    flex-grow: 1;
    border-left: rgb(var(--grey-3));
    background-color: var(--color-bg-2);
  }

  .a-tree {
    border-right: 1px solid var(--color-border);
    min-width: 200px;
    padding-right: 52px;
    padding-top: 10px;
    padding-left: 2px;
  }
  :deep(.arco-tree),
  :deep(.arco-tree-size-medium),
  :deep(.tree-demo) {
    min-width: 200px;
    max-width: 200px;
  }
  .tree-demo :deep(.tree-node-dropover) > :deep(.arco-tree-node-title),
  .tree-demo :deep(.tree-node-dropover) > :deep(.arco-tree-node-title):hover {
    animation: blinkBg 0.4s 2;
  }

  @keyframes blinkBg {
    0% {
      background-color: transparent;
    }

    100% {
      background-color: var(--color-primary-light-1);
    }
  }
  .dropdown-div {
    display: flex;
    width: 121px;
    align-items: center;
    margin-bottom: 0px;
    gap: 10px;
    .text {
      color: var(--color-text-1);
      font-size: 11.5px;
      font-style: normal;
      font-weight: 800;
      line-height: normal;
    }
  }
  .menu-search {
    flex-grow: 1;
    // height: 36px;
    .search {
      padding-left: 18px;
      margin-right: 20px;
      font-size: 14px;
      padding-top: 5px;
      background-color: var(--color-bg-2);
    }
    border-left: rgb(var(--grey-3));
    border-bottom: rgb(var(--grey-3));
  }
  :deep(.arco-input-wrapper .arco-input.arco-input-size-large) {
    font-size: 17px;
  }
</style>
